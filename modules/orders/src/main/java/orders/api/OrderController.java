package orders.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orders.api.dto.CreateOrderRequest;
import orders.api.dto.OrderResponse;
import orders.mapper.OrderMapper;
import orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders API")
public class OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestHeader("user_id") long userId,
                                @Valid @RequestBody CreateOrderRequest req) {
        return mapper.toDto(service.createOrder(userId, req.amount()));
    }

    @GetMapping
    public List<OrderResponse> list(@RequestHeader("user_id") long userId) {
        return service.userOrders(userId).stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public OrderResponse one(@PathVariable long id) {
        return mapper.toDto(service.get(id));
    }
}