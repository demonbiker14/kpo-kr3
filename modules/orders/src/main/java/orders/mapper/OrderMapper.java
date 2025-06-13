package orders.mapper;

import orders.api.dto.OrderResponse;
import orders.dto.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toDto(Order order);
}