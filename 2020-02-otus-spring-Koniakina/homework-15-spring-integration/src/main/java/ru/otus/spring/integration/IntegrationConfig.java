package ru.otus.spring.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.otus.spring.model.Notification;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.model.enums.DeliveryStatus;
import ru.otus.spring.model.enums.DeliveryType;
import ru.otus.spring.service.DeliveryService;
import ru.otus.spring.service.ErrorHandler;
import ru.otus.spring.service.PostService;

import java.util.concurrent.Executor;

@Configuration
@IntegrationComponentScan
@RequiredArgsConstructor
public class IntegrationConfig {

    private static final int DEFAULT_POOL_SIZE = 5;
    private static final int EVENT_MAX_POOL_SIZE = 10;
    private static final int DELIVERY_MAX_POOL_SIZE = 10;

    private final PostService postService;
    private final DeliveryService deliveryService;
    private final ErrorHandler errorHandler;

    public static final String DELIVERY_STATUS = "delivery_status";

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel parcelsChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    @Qualifier(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public MessageChannel errorChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel notificationChannel() {
        return MessageChannels.executor(notificationExecutor()).get();
    }

    @Bean
    public Executor notificationExecutor() {
        return newThreadPoolTask("notification", DEFAULT_POOL_SIZE, EVENT_MAX_POOL_SIZE);
    }

    @Bean
    public PollableChannel postChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public MessageChannel deliveryChannel() {
        return MessageChannels.executor(deliveryExecutor()).get();
    }

    @Bean
    public Executor deliveryExecutor() {
        return newThreadPoolTask("delivery", DEFAULT_POOL_SIZE, DELIVERY_MAX_POOL_SIZE);
    }

    @Bean
    public MessageChannel landChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public ThreadPoolTaskExecutor landExecutor() {
        return newThreadPoolTask("land", DEFAULT_POOL_SIZE, 10);
    }

    @Bean
    public MessageChannel airChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public ThreadPoolTaskExecutor airExecutor() {
        return newThreadPoolTask("air", DEFAULT_POOL_SIZE, 10);
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).get();
    }

    @Bean
    public IntegrationFlow errorHandlingFlow() {
        return IntegrationFlows.from(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                .handle(errorHandler, "handleMessage")
                .get();
    }

    @Bean
    public IntegrationFlow notificationFlow() {
        return IntegrationFlows.from("notificationChannel")
                .<Notification>log(
                        "notification>",
                        notificationMessage -> Notification.NOTIFICATION_STRING_FUNCTION.apply(notificationMessage.getPayload())
                )
                .get();
    }

    @Bean
    public IntegrationFlow ordersFlow() {
        return IntegrationFlows.from("ordersChannel")
                .log("order>")
                // Группируем все заказы, сделанные в течение часа (но не более 10)
                .aggregate(a -> a
                        .sendPartialResultOnExpiry(true)
                        .releaseStrategy(new TimeoutCountSequenceSizeReleaseStrategy(10, 60*60*1000))
                )
                // Привозим собранные заказы на почту
                .channel(postChannel())
                .get();
    }

    @Bean
    public IntegrationFlow postFlow() {
        return IntegrationFlows.from("postChannel")
                .log("post>")
                // Упаковываем посылку, присваиваем trackNumber
                .transform(postService, "transformToParcel")
                // Уведомляем клиента о присвоенном трек-номере
                .handle(postService, "sendNotificationAboutPackingOfParcel")
                // Отправляем посылку
                .channel(parcelsChannel())
                .get();
    }

    @Bean
    public IntegrationFlow deliveryFlow() {
        return IntegrationFlows.from("deliveryChannel")
                .log("delivery>")
                // Выбираем вид транспорта, которым будет доставлена посылка
                .handle(deliveryService, "setDeliveryType")
                // Уведомляем клиента о начале транспортировки
                .handle(postService, "sendNotificationOfTransportationStart")
                // В зависимости от вида транспортировки направляем в соответствующий канал
                .<Parcel, DeliveryType>route(Parcel::getDeliveryType,
                        router -> router
                                .channelMapping(DeliveryType.LAND, landChannel())
                                .channelMapping(DeliveryType.AIR, airChannel())
                )
                .get();
    }

    @Bean
    public IntegrationFlow landFlow() {
        return IntegrationFlows.from("landChannel")
                .channel(MessageChannels.executor(landExecutor()))
                // Обновляем статус заказа
                .enrichHeaders(headers -> headers.header(DELIVERY_STATUS, DeliveryStatus.DELIVERED))
                // Уведомляем клиента об успешной доставке
                .handle(postService, "sendNotificationOfSuccessfulDelivery")
                .channel(parcelsChannel())
                .get();
    }

    @Bean
    public IntegrationFlow airFlow() {
        return IntegrationFlows.from("airChannel")
                .channel(MessageChannels.executor(airExecutor()))
                // Обновляем статус заказа
                .enrichHeaders(headers -> headers.header(DELIVERY_STATUS, DeliveryStatus.DELIVERED))
                // Уведомляем клиента об успешной доставке
                .handle(postService, "sendNotificationOfSuccessfulDelivery")
                .channel(parcelsChannel())
                .get();
    }

    private static ThreadPoolTaskExecutor newThreadPoolTask(String prefix, int coreSize, int maxPoolSize) {
        return new TaskExecutorBuilder()
                .corePoolSize(coreSize)
                .maxPoolSize(maxPoolSize)
                .threadNamePrefix(prefix + "-")
                .awaitTermination(true)
                .build();
    }
}
