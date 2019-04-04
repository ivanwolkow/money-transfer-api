package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import one.util.streamex.EntryStream;
import org.example.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAccountRepository implements AccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryAccountRepository.class);
    private static final String INITIAL_ACCOUNTS_FILEPATH = "/initial_accounts.yml";

    private Map<Long, AccountEntity> accounts;

    public InMemoryAccountRepository() {
        try (InputStream is = InMemoryAccountRepository.class.getResourceAsStream(INITIAL_ACCOUNTS_FILEPATH)) {
            final Map<Long, BigDecimal> initialAccounts = new ObjectMapper(new YAMLFactory())
                    .readerFor(new TypeReference<Map<Long, BigDecimal>>() {}).readValue(is);

            accounts = EntryStream.of(initialAccounts)
                    .mapToValue(AccountEntity::new)
                    .toCustomMap(ConcurrentHashMap::new);

        } catch (IOException e) {
            logger.error("Unable to initialize in-memory accounts!");
        }
    }

    @Override
    public AccountEntity getAccountById(Long id) {
        return accounts.get(id);
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void updateAccount(AccountEntity accountEntity) {
        accounts.put(accountEntity.getId(), accountEntity);
    }
}
