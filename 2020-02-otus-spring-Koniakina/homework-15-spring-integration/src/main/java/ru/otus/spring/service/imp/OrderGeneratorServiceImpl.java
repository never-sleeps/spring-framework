package ru.otus.spring.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.ShopGateway;
import ru.otus.spring.service.OrderGeneratorService;

import static ru.otus.spring.utils.Util.getRandomOrderForGeneration;

@Service
@RequiredArgsConstructor
public class OrderGeneratorServiceImpl implements OrderGeneratorService {

    private final ShopGateway shopGateway;

    @Override
    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    public void generateOrder() {
        shopGateway.process(getRandomOrderForGeneration());
    }
}
