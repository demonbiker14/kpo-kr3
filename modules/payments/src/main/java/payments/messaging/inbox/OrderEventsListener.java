package payments.messaging.inbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import payments.messaging.event.OrderCreatedEvent;
import payments.service.AccountService;

import java.time.OffsetDateTime;

import static payments.messaging.config.RabbitConfig.ORDER_CREATED_QUEUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventsListener {

    private final AccountService accountService;
    private final InboxRepository inboxRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void handleOrderCreated(String payload,
                                   @Header(value = "message_id", required = false) String messageId) {
        if (messageId != null && inboxRepository.existsById(messageId)) {
            log.info("Duplicate message {} ignored", messageId);
            return;
        }
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            accountService.debitForOrder(event.getUserId(), event.getAmount(), event.getOrderId());
            inboxRepository.save(InboxMessage.builder()
                    .messageId(messageId)
                    .receivedAt(OffsetDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Failed to process order created event", e);
            throw new RuntimeException(e);
        }
    }
}