package payments.messaging.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private int amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime occurredAt;
}