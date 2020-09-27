package ru.otus.spring.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import ru.otus.spring.model.Customer;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.OrderItem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util {
    private final static List<OrderItem> ORDER_ITEMS = ImmutableList.of(
            new OrderItem("Тетрадь", BigDecimal.valueOf(50.00)),
            new OrderItem("Учебник", BigDecimal.valueOf(700.00)),
            new OrderItem("Пенал", BigDecimal.valueOf(200.00)),
            new OrderItem("Ручка", BigDecimal.valueOf(50.00)),
            new OrderItem("Карандаш", BigDecimal.valueOf(40.00)),
            new OrderItem("Линейка", BigDecimal.valueOf(50.00)),
            new OrderItem("Обложка", BigDecimal.valueOf(5.50)),
            new OrderItem("Точилка", BigDecimal.valueOf(25.00)),
            new OrderItem("Ластик", BigDecimal.valueOf(10.70)),
            new OrderItem("Альбом", BigDecimal.valueOf(220.00))
    );

    private final static List<Customer> CUSTOMERS = ImmutableList.of(
            new Customer("Иванов Александр","ivan@mail.ru","г.Москва, ул.Садовая 5"),
            new Customer("Петров Никита","nikita@gmail.com","г.Казань, ул.Кедровая 20"),
            new Customer("Сидорова Анна","anna@yandex.ru","г.Новосибирск, ул.Зорге 66"),
            new Customer("Фёдорова Елена","elena@inbox.ru","г.Тамбов, ул.Дзержинского 16"),
            new Customer("Васильев Сергей","sergey@gmail.com","г.Москва, Кутузовский пр-т 9")
    );

    public static <T> T getRandomItem(List<T> items) {
        return items.get(RandomUtils.nextInt(0, items.size()));
    }

    public static Order getRandomOrderForGeneration(){
        Customer customer = getRandomItem(CUSTOMERS);
        Map<OrderItem, Integer> orderItems = new HashMap<>();
        for (int i = 0; i < RandomUtils.nextInt(0, 5); i++) {
            orderItems.put(getRandomItem(ORDER_ITEMS), RandomUtils.nextInt(0, 3));
        }
        return new Order(customer, orderItems);
    }
}
