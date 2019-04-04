package org.example.configuration;

import com.google.inject.AbstractModule;
import org.example.controller.AccountController;
import org.example.controller.TransferController;
import org.example.repository.AccountRepository;
import org.example.repository.InMemoryAccountRepository;
import org.example.service.AccountService;
import org.example.service.TransferService;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransferController.class).asEagerSingleton();
        bind(AccountController.class).asEagerSingleton();
        bind(TransferService.class).asEagerSingleton();
        bind(AccountService.class).asEagerSingleton();
        bind(AccountRepository.class).to(InMemoryAccountRepository.class).asEagerSingleton();
    }

}
