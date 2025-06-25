
package payments.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    public static final String ORDER_EVENTS_EXCHANGE = "order.events";
    public static final String PAYMENT_EVENTS_EXCHANGE = "payment.events";
    public static final String ORDER_CREATED_QUEUE = "order.created";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    @Bean
    @Primary
    public TopicExchange orderEventsExchange() {
        return new TopicExchange(ORDER_EVENTS_EXCHANGE, true, false);
    }

    @Bean
    public TopicExchange paymentEventsExchange() {
        return new TopicExchange(PAYMENT_EVENTS_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable(ORDER_CREATED_QUEUE).build();
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange orderEventsExchange) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(orderEventsExchange)
                .with(ORDER_CREATED_ROUTING_KEY);
    }
}