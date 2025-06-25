package payments.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TopUpRequest(@NotNull @Min(1) int amount) {
}
