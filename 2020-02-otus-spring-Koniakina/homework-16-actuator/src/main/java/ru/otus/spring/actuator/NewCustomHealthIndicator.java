package ru.otus.spring.actuator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.repository.BookRepository;

@Component
@AllArgsConstructor
public class NewCustomHealthIndicator implements HealthIndicator {

  private BookRepository bookRepository;

  @Override
  public Health health() {
    if (checkExistsJavaBook()) {
      return Health.up()
              .withDetail("message", "Java в библиотеке есть")
              .build();
    } else {
      return Health.down()
              .status(Status.DOWN)
              .withDetail("message", "Срочно нужна книга про Java!")
              .build();
    }
  }

  private boolean checkExistsJavaBook(){
    return bookRepository.findByTitleIsLike("%java%").isPresent();
  }
}
