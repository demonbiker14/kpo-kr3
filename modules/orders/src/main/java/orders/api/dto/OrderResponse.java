package orders.api.dto;

import orders.dto.OrderStatus;

import java.time.Instant;

public record OrderResponse(
        Long id, int amount,
                            OrderStatus status, Instant createdAt) {
}