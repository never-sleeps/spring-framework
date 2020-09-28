package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.ShopGateway;

import static ru.otus.spring.utils.Util.getRandomOrderForGeneration;

@Service
@RequiredArgsConstructor
public class OrderGeneratorService {

    private final ShopGateway shopGateway;

    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    public void generateOrder() {
        shopGateway.process(getRandomOrderForGeneration());
    }
}
