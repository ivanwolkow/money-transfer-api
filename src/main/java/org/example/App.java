package org.example;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.example.configuration.BasicModule;
import org.example.configuration.DropwizardConfiguration;
import org.example.controller.AccountController;
import org.example.controller.TransferController;
import org.example.exception.mapper.BaseExceptionMapper;
import org.example.healthcheck.DefaultHealthCheck;

public class App extends Application<DropwizardConfiguration> {

    @Inject
    TransferController transferController;
    @Inject
    AccountController accountController;

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());
        injector.getInstance(App.class).run(args);
    }

    @Override
    public void run(DropwizardConfiguration dropwizardConfiguration, Environment environment) {
        environment.healthChecks().register(DefaultHealthCheck.class.getSimpleName(), new DefaultHealthCheck());

        environment.jersey().register(new BaseExceptionMapper());

        environment.jersey().register(transferController);
        environment.jersey().register(accountController);
    }

}
