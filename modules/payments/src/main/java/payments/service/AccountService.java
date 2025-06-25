package payments.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.dto.Account;
import payments.dto.AccountRepository;
import payments.messaging.event.OrderPaidEvent;
import payments.messaging.outbox.OutboxMessage;
import payments.messaging.outbox.OutboxRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final OutboxRepository outboxRepository;

    @Transactional
    public Account createAccount(Long userId, int initialBalance) {
        if (accountRepository.existsById(userId)) {
            throw new IllegalStateException("Account already exists for user " + userId);
        }
        return accountRepository.save(Account.builder()
                .userId(userId)
                .balance(initialBalance)
                .build());
    }

    @Transactional
    public Account topUp(Long userId, int amount) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return account;
    }

    @Transactional(readOnly = true)
    public Account getAccount(Long userId) {
        return accountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    @Transactional
    public void debitForOrder(Long userId, int amount, Long orderId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        boolean success = account.getBalance() >= amount;
        if (success) {
            account.setBalance(account.getBalance() - amount);
        }
        OrderPaidEvent event = new OrderPaidEvent(orderId, userId, success, OffsetDateTime.now());

        outboxRepository.save(OutboxMessage.builder()
                .aggregateType("Order")
                .aggregateId(orderId)
                .type("order.paid")
                .payload(JsonUtil.toJson(event))
                .createdAt(OffsetDateTime.now())
                .build());
    }
}