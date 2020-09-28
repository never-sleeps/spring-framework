package ru.otus.spring.service.imp;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.exception.PostException;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public Parcel transformToParcel(Order order) {
        // Моделируем ошибку формирования посылки для трети всех случаев
        if (order.getId( )% 3 == 0) {
            throw new PostException("Ошибка формирования посылки №" + order.getId());
        }
        return Parcel.builder()
                .trackNumber(generateTrackNumber(order))
                .order(order)
                .build();
    }

    private String generateTrackNumber(Order order) {
        return "" + Math.abs(order.getItems().hashCode());
    }
}
