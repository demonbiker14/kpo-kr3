package payments.messaging.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import payments.messaging.config.RabbitConfig;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000)
    public void publish() {
        outboxRepository.findTop100BySentFalseOrderByCreatedAtAsc()
                .forEach(message -> {
                    try {
                        rabbitTemplate.convertAndSend(RabbitConfig.PAYMENT_EVENTS_EXCHANGE,
                                message.getType(),
                                objectMapper.readTree(message.getPayload()));
                        message.setSent(true);
                        outboxRepository.save(message);
                    } catch (Exception e) {
                        log.error("Failed to publish outbox message {}", message.getId(), e);
                    }
                });
    }
}