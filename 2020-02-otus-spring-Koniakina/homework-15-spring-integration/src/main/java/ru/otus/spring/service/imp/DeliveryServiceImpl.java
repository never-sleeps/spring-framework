package ru.otus.spring.service.imp;

import org.springframework.stereotype.Service;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.model.enums.DeliveryType;
import ru.otus.spring.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public Parcel setDeliveryType(Parcel parcel) {
        parcel.setDeliveryType(DeliveryType.getRandom());
        return parcel;
    }
}
