package payments.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import payments.api.dto.AccountBalanceResponse;
import payments.api.dto.CreateAccountRequest;
import payments.api.dto.TopUpRequest;
import payments.mapper.AccountMapper;
import payments.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountBalanceResponse createAccount(@RequestHeader("user_id") Long userId,
                                                @Valid @RequestBody CreateAccountRequest body) {
        var account = accountService.createAccount(userId, body.initialBalance());
        return accountMapper.toBalanceResponse(account);
    }

    @PostMapping("/top-up")
    public AccountBalanceResponse topUp(@RequestHeader("user_id") Long userId,
                                        @Valid @RequestBody TopUpRequest body) {
        var account = accountService.topUp(userId, body.amount());
        return accountMapper.toBalanceResponse(account);
    }

    @GetMapping("/balance")
    public AccountBalanceResponse getBalance(@RequestHeader("user_id") Long userId) {
        var account = accountService.getAccount(userId);
        return accountMapper.toBalanceResponse(account);
    }
}