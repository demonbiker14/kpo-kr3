package payments.messaging.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class OrderPaidEvent {
    private Long orderId;
    private Long userId;
    private boolean successful;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime occurredAt;
}
