package payments.mapper;

import org.mapstruct.Mapper;
import payments.api.dto.AccountBalanceResponse;
import payments.dto.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountBalanceResponse toBalanceResponse(Account account);
}