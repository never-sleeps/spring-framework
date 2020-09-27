package ru.otus.spring.service;

import ru.otus.spring.model.Parcel;

public interface DeliveryService {
    /**
     * указываем вид транспорта, которым будет доставлена посылка
     * @param parcel посылка без указания вида транспорта
     * @return посылка с указанием трансорта, которым она будет доставлена
     */
    Parcel setDeliveryType(Parcel parcel);
}
