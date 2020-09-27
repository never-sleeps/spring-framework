package ru.otus.spring.service;

import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;

public interface PostService {

    /**
     * Упаковываем посылку, присваиваем трек-номер
     * @param order заказ
     * @return посылка
     */
    Parcel transformToParcel(Order order);

    /**
     * Уведомляем клиента об упаковке посылки и присвоении ей трек-номера
     * @param parcel посылка
     */
    void sendNotificationAboutPackingOfParcel(Parcel parcel);

    /**
     * Уведомляем клиента о начале транспортировки
     * @param parcel посылка
     */
    void sendNotificationOfTransportationStart(Parcel parcel);

    /**
     * Уведомляем клиента об успешной доставке посылки
     * @param parcel посылка
     */
    void sendNotificationOfSuccessfulDelivery(Parcel parcel);

    /**
     * Уведомляем клиента об ошибке доставки посылки
     * @param parcel посылка
     */
    void sendNotificationError(Parcel parcel);
}
