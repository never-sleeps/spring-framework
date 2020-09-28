package ru.otus.spring.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import ru.otus.spring.model.Customer;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.OrderItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Util {

    private final static AtomicLong ID = new AtomicLong(1);

    private final static List<OrderItem> ORDER_ITEMS = ImmutableList.of(
            new OrderItem("Тетрадь", getRandomPrice()),
            new OrderItem("Учебник", getRandomPrice()),
            new OrderItem("Пенал", getRandomPrice()),
            new OrderItem("Ручка", getRandomPrice()),
            new OrderItem("Карандаш", getRandomPrice()),
            new OrderItem("Линейка", getRandomPrice()),
            new OrderItem("Обложка", getRandomPrice()),
            new OrderItem("Точилка", getRandomPrice()),
            new OrderItem("Ластик", getRandomPrice()),
            new OrderItem("Альбом", getRandomPrice())
    );

    private static BigDecimal getRandomPrice(){
        return BigDecimal.valueOf(RandomUtils.nextDouble(0.50, 500)).setScale(2, RoundingMode.CEILING);
    }

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
        for (int i = 0; i < RandomUtils.nextInt(1, 5); i++) {
            orderItems.put(getRandomItem(ORDER_ITEMS), RandomUtils.nextInt(1, 5));
        }
        return Order.builder()
                .id(ID.getAndIncrement())
                .customer(customer)
                .items(orderItems)
                .build();
    }
}
