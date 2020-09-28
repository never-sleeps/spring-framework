package ru.otus.spring.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;
import ru.otus.spring.service.ErrorHandler;
import ru.otus.spring.service.NotificationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorHandlerImpl implements ErrorHandler {

    private final NotificationService notificationService;

    @Override
    public void handleMessage(ErrorMessage errorMessage) {
        Throwable t = errorMessage.getPayload();
        log.error(t.getMessage());

        if (t instanceof MessagingException) {
            Message<?> message = ((MessagingException) t).getFailedMessage();
            Object originalPayload = message.getPayload();
            sendNotificationError(originalPayload);
        }
    }

    private void sendNotificationError(Object obj) {
        if (obj instanceof Parcel) {
            notificationService.sendNotificationError((Parcel) obj);
        } else if (obj instanceof Order) {
            notificationService.sendNotificationError((Order) obj);
        }
    }
}