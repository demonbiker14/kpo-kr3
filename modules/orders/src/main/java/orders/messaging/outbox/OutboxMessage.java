package orders.messaging.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import orders.messaging.event.OrderCreatedEvent;

import java.time.Instant;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "outbox", indexes = @Index(columnList = "processed"))
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String aggregateType; // e.g. "Order"
    private Long aggregateId;

    @Column(columnDefinition = "jsonb")
    private String payload;

    private boolean processed;
    private Instant createdAt;

    public static OutboxMessage of(Object event) {
        ObjectMapper objectMapper = new ObjectMapper();
        String eventString;
        try {
            eventString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event to JSON", e);
        }
        return OutboxMessage.builder()
                .aggregateType(event.getClass().getSimpleName())
                .aggregateId(
                        event instanceof OrderCreatedEvent _event ? _event.orderId() : null)
                .payload(eventString)
                .processed(false)
                .createdAt(Instant.now())
                .build();
    }
}
