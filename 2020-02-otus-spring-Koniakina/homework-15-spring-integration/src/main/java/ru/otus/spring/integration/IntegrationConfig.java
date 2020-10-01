package ru.otus.spring.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import ru.otus.spring.model.Notification;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.model.enums.DeliveryType;
import ru.otus.spring.service.DeliveryService;
import ru.otus.spring.service.ErrorHandler;
import ru.otus.spring.service.NotificationService;
import ru.otus.spring.service.PostService;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@RequiredArgsConstructor
public class IntegrationConfig {

    private final PostService postService;
    private final DeliveryService deliveryService;
    private final NotificationService notificationService;
    private final ErrorHandler errorHandler;

    public static final String ORDERS_CHANNEL = "ordersChannel";
    public static final String NOTIFICATION_CHANNEL = "notificationChannel";
    public static final String POST_CHANNEL = "postChannel";
    public static final String DELIVERY_CHANNEL = "deliveryChannel";
    public static final String LAND_CHANNEL = "landChannel";
    public static final String AIR_CHANNEL = "airChannel";


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).get();
    }

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public MessageChannel notificationChannel() {
        return MessageChannels.queue(50).get();
    }

    @Bean
    @Qualifier(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public MessageChannel errorChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PollableChannel postChannel() { return MessageChannels.queue(10).get(); }

    @Bean
    public MessageChannel deliveryChannel() { return MessageChannels.queue(50).get();}

    @Bean
    public MessageChannel landChannel() {
        return MessageChannels.queue(50).get();
    }

    @Bean
    public MessageChannel airChannel() {
        return MessageChannels.queue(50).get();
    }

    @Bean
    public IntegrationFlow errorHandlingFlow() {
        return IntegrationFlows.from(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                .handle(errorHandler, "handleMessage")
                .get();
    }

    @Bean
    public IntegrationFlow notificationFlow() {
        return IntegrationFlows.from(NOTIFICATION_CHANNEL)
                .<Notification>log("notification >", m -> Notification.NOTIFICATION_STRING_FUNCTION.apply(m.getPayload()))
                .get();
    }

    @Bean
    public IntegrationFlow ordersFlow() {
        return IntegrationFlows.from(ORDERS_CHANNEL)
                .log("create order >")
                .handle(notificationService, "sendNotificationAboutOrderRegistered")
                .channel(POST_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow postFlow() {
        return IntegrationFlows.from(POST_CHANNEL)
                .log("post office >")
                .transform(postService, "transformToParcel")
                .handle(notificationService, "sendNotificationAboutPackingOfParcel")
                .channel(DELIVERY_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow deliveryFlow() {
        return IntegrationFlows.from(DELIVERY_CHANNEL)
                .log("start delivery >")
                .handle(deliveryService, "setDeliveryType")
                .handle(notificationService, "sendNotificationOfTransportationStart")
                .<Parcel, DeliveryType>route(Parcel::getDeliveryType,
                        router -> router
                                .channelMapping(DeliveryType.LAND, LAND_CHANNEL)
                                .channelMapping(DeliveryType.AIR, AIR_CHANNEL)
                )
                .get();
    }

    @Bean
    public IntegrationFlow landFlow() {
        return IntegrationFlows.from(LAND_CHANNEL)
                .log("delivery by land >")
                .handle(notificationService, "sendNotificationOfSuccessfulDelivery")
                .get();
    }

    @Bean
    public IntegrationFlow airFlow() {
        return IntegrationFlows.from(AIR_CHANNEL)
                .log("delivery by air >")
                .handle(notificationService, "sendNotificationOfSuccessfulDelivery")
                .get();
    }
}
