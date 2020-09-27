package ru.otus.spring.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.NotificationGateway;
import ru.otus.spring.model.Customer;
import ru.otus.spring.model.Notification;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.model.enums.DeliveryType;
import ru.otus.spring.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final NotificationGateway notifyGateway;

    @Override
    public Parcel transformToParcel(Order order) {
        return null;
    }

    @Override
    public void sendNotificationAboutPackingOfParcel(Parcel parcel) {
        String text = String.format("%s, ваш заказ был успешно сформирован. Номер для отслеживания '%s'",
                parcel.getOrder().getCustomer().getName(), parcel.getTrackNumber());
        sendNotification(text, parcel.getOrder().getCustomer());
    }


    @Override
    public void sendNotificationOfTransportationStart(Parcel parcel) {
        String text = String.format("%s, ваш заказ №%s будет доставлен %s",
                parcel.getOrder().getCustomer().getName(), parcel.getTrackNumber(),
                (parcel.getDeliveryType() == DeliveryType.LAND) ? "поездом" : "самолётом");
        sendNotification(text, parcel.getOrder().getCustomer());
    }

    @Override
    public void sendNotificationOfSuccessfulDelivery(Parcel parcel) {
        String text = String.format("%s, ваш заказ №%s успешно доставлен. Заберите его в вашем отделении почты.",
                parcel.getOrder().getCustomer().getName(), parcel.getTrackNumber());
        sendNotification(text, parcel.getOrder().getCustomer());
    }

    @Override
    public void sendNotificationError(Parcel parcel) {
        String text = String.format("%s, сожалеем, ваш заказ №%s не может быть доставлен. Деньги будут возвращены в течение суток.",
                parcel.getOrder().getCustomer().getName(), parcel.getTrackNumber());
        sendNotification(text, parcel.getOrder().getCustomer());
    }

    private void sendNotification(String message, Customer customer) {
        Notification notification = Notification.builder()
                .email(customer.getEmail())
                .text(message)
                .build();
        notifyGateway.process(notification);
    }
}
