package org.example.service;

import com.google.inject.Inject;
import org.example.entity.AccountEntity;
import org.example.exception.AccountNotFoundException;
import org.example.exception.NotEnoughMoneyException;
import org.example.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class TransferService {

    private final Map<Long, Object> locks = new WeakHashMap<>();

    @Inject
    private AccountRepository accountRepository;

    public void transfer(Long srcAccountId, Long dstAccountId, BigDecimal amount) {

        final Long firstId = srcAccountId.compareTo(dstAccountId) < 0 ? srcAccountId : dstAccountId;
        final Long secondId = srcAccountId.compareTo(dstAccountId) < 0 ? dstAccountId : srcAccountId;

        Object lock1;
        Object lock2;

        synchronized (locks) {
            lock1 = locks.computeIfAbsent(firstId, (id) -> new Object());
            lock2 = locks.computeIfAbsent(secondId, (id) -> new Object());
        }

        synchronized (lock1) {
            synchronized (lock2) {
                final AccountEntity srcAccount = Optional.ofNullable(accountRepository.getAccountById(srcAccountId))
                        .orElseThrow(() -> new AccountNotFoundException(srcAccountId));
                final AccountEntity dstAccount = Optional.ofNullable(accountRepository.getAccountById(dstAccountId))
                        .orElseThrow(() -> new AccountNotFoundException(dstAccountId));

                if (amount.compareTo(srcAccount.getAmount()) > 0) {
                    throw new NotEnoughMoneyException();
                }

                srcAccount.setAmount(srcAccount.getAmount().subtract(amount));
                dstAccount.setAmount(dstAccount.getAmount().add(amount));

                accountRepository.updateAccount(srcAccount);
                accountRepository.updateAccount(dstAccount);
            }
        }

    }

}
