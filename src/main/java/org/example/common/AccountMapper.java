package org.example.common;

import org.example.dto.AccountDto;
import org.example.entity.AccountEntity;

public class AccountMapper {

    public static AccountDto map(AccountEntity accountEntity) {
        return new AccountDto(accountEntity.getId(), accountEntity.getAmount());
    }
}
