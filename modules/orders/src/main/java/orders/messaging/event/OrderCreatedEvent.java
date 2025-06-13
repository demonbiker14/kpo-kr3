package orders.messaging.event;

import java.io.Serializable;

public record OrderCreatedEvent(Long orderId, Long userId, int amount) implements Serializable {
}