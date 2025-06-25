package orders.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import orders.dto.Order;
import orders.dto.OrderStatus;
import orders.messaging.event.OrderCreatedEvent;
import orders.messaging.outbox.OutboxMessage;
import orders.messaging.outbox.OutboxRepository;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OutboxRepository outbox;

    @Transactional
    public Order createOrder(Long userId, int amount) {
        Order order = Order.builder()
                .userId(userId)
                .amount(amount)
                .status(OrderStatus.NEW)
                .build();
        repo.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), userId, amount);
        outbox.save(OutboxMessage.of(event));
        return order;
    }

    public List<Order> userOrders(Long userId) {
        return repo.findByUserId(userId);
    }

    public Order get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order " + id));
    }
}