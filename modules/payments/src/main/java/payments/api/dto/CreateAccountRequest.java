package payments.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record CreateAccountRequest(@NotNull @Min(0) int initialBalance) {
}
