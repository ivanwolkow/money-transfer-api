package org.example.service;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.example.common.AccountMapper;
import org.example.dto.AccountDto;
import org.example.exception.AccountNotFoundException;
import org.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {

    @Inject
    AccountRepository accountRepository;

    public AccountDto getAccountById(Long id) {
        return Optional.ofNullable(accountRepository.getAccountById(id))
                .map(AccountMapper::map)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<AccountDto> getAllAccounts() {
        return StreamEx.of(accountRepository.getAllAccounts())
                .map(AccountMapper::map)
                .collect(Collectors.toList());
    }

}
