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
}
