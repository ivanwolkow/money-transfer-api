package org.example.repository;

import org.example.entity.AccountEntity;

import java.util.List;

public interface AccountRepository {

    AccountEntity getAccountById(Long id);

    List<AccountEntity> getAllAccounts();

    void updateAccount(AccountEntity accountEntity);

}
