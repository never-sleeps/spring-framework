package ru.otus.spring.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.NotificationGateway;
import ru.otus.spring.model.Customer;
import ru.otus.spring.model.Notification;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.model.enums.DeliveryType;
import ru.otus.spring.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationGateway notifyGateway;

    @Override
    public Order sendNotificationAboutOrderRegistered(Order order) {
        String text = String.format("%s, ваш заказ №%s зарегистрирован.",
                order.getCustomer().getName(),
                order.getId()
        );
        sendNotification(text, order.getCustomer());
        return order;
    }

    @Override
    public Parcel sendNotificationAboutPackingOfParcel(Parcel parcel) {
        String text = String.format("%s, ваш заказ №%s был успешно сформирован. Номер для отслеживания #%s",
                parcel.getOrder().getCustomer().getName(),
                parcel.getOrder().getId(),
                parcel.getTrackNumber()
        );
        sendNotification(text, parcel.getOrder().getCustomer());
        return parcel;
    }


    @Override
    public Parcel sendNotificationOfTransportationStart(Parcel parcel) {
        String text = String.format("%s, ваш заказ №%s (#%s) будет доставлен %s",
                parcel.getOrder().getCustomer().getName(),
                parcel.getOrder().getId(),
                parcel.getTrackNumber(),
                (parcel.getDeliveryType() == DeliveryType.LAND) ? "поездом" : "самолётом");
        sendNotification(text, parcel.getOrder().getCustomer());
        return parcel;
    }

    @Override
    public Parcel sendNotificationOfSuccessfulDelivery(Parcel parcel) {
        String text = String.format("%s, ваш заказ №%s (#%s) успешно доставлен. Заберите его в вашем отделении почты.",
                parcel.getOrder().getCustomer().getName(),
                parcel.getOrder().getId(),
                parcel.getTrackNumber());
        sendNotification(text, parcel.getOrder().getCustomer());
        return parcel;
    }

    @Override
    public Parcel sendNotificationError(Parcel parcel) {
        String text = String.format("%s, сожалеем, ваш заказ №%s (#%s) не может быть доставлен. Деньги будут возвращены в течение суток.",
                parcel.getOrder().getCustomer().getName(),
                parcel.getOrder().getId(),
                parcel.getTrackNumber());
        sendNotification(text, parcel.getOrder().getCustomer());
        return parcel;
    }

    @Override
    public Order sendNotificationError(Order order) {
        String text = String.format("%s, сожалеем, ваш заказ №%s не может быть доставлен. Деньги будут возвращены в течение суток.",
                order.getCustomer().getName(),
                order.getId());
        sendNotification(text, order.getCustomer());
        return order;
    }

    private void sendNotification(String message, Customer customer) {
        Notification notification = Notification.builder()
                .email(customer.getEmail())
                .text(message)
                .build();
        notifyGateway.process(notification);
    }
}
