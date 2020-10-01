package ru.otus.spring.service;

import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;

public interface NotificationService {

    /**
     * Уведомляем клиента о том, что его заказ зарегистрирован
     * @param order посылка
     */
    Order sendNotificationAboutOrderRegistered(Order order);

    /**
     * Уведомляем клиента об упаковке посылки и присвоении ей трек-номера
     * @param parcel посылка
     */
    Parcel sendNotificationAboutPackingOfParcel(Parcel parcel);

    /**
     * Уведомляем клиента о начале транспортировки
     * @param parcel посылка
     */
    Parcel sendNotificationOfTransportationStart(Parcel parcel);

    /**
     * Уведомляем клиента об успешной доставке посылки
     * @param parcel посылка
     */
    Parcel sendNotificationOfSuccessfulDelivery(Parcel parcel);

    /**
     * Уведомляем клиента об ошибке доставки посылки
     * @param parcel посылка
     */
    Parcel sendNotificationError(Parcel parcel);

    /**
     * Уведомляем клиента об ошибке доставки заказа
     * @param order заказ
     */
    Order sendNotificationError(Order order);
}
