package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.model.enums.DeliveryType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
    private String trackNumber;
    private Order order;
    private DeliveryType deliveryType;
}
