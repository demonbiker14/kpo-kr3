package orders.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi ordersApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .packagesToScan("com.shop.orders.api")
                .build();
    }
}