package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import one.util.streamex.StreamEx;
import org.example.configuration.BasicModule;
import org.example.controller.AccountController;
import org.example.controller.TransferController;
import org.example.dto.AccountDto;
import org.example.dto.TransferRequestDto;
import org.example.exception.NotEnoughMoneyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class TransferControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(TransferControllerTest.class);
    private final static Integer TEST_ACCOUNT_NUMBER = 5;
    private final static Double MAX_TRANSFER_AMOUNT = 10.;
    private final static Integer CONCURRENT_TRANSFERS_COUNT = 100000;
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    private TransferController transferController;
    private AccountController accountController;

    @Before
    public void init() {
        Injector injector = Guice.createInjector(new BasicModule());
        transferController = injector.getInstance(TransferController.class);
        accountController = injector.getInstance(AccountController.class);
    }

    @Test
    public void testSimpleTransferShouldSucceed() {
        final BigDecimal firstAccountAmountBefore = accountController.getAccountById(1L).getAmount();
        final BigDecimal secondAccountAmountBefore = accountController.getAccountById(2L).getAmount();

        transferController.transfer(new TransferRequestDto(1L, 2L, new BigDecimal("10.00")));

        final BigDecimal firstAccountAmountAfter = accountController.getAccountById(1L).getAmount();
        final BigDecimal secondAccountAmountAfter = accountController.getAccountById(2L).getAmount();

        Assert.assertEquals(firstAccountAmountBefore.subtract(new BigDecimal("10.00")), firstAccountAmountAfter);
        Assert.assertEquals(secondAccountAmountBefore.add(new BigDecimal("10.00")), secondAccountAmountAfter);
    }

    @Test
    public void testConcurrentTransfersShouldntBreakConsistency() throws InterruptedException {
        final BigDecimal totalBefore = StreamEx.of(accountController.getAllAccounts())
                .map(AccountDto::getAmount)
                .reduce((BigDecimal::add))
                .map(b -> b.setScale(2, RoundingMode.FLOOR))
                .get();

        logger.info("Total balance before: {}, account states:", totalBefore);
        StreamEx.of(accountController.getAllAccounts()).forEach(a -> logger.info(a.toString()));

        final ExecutorService executorService = Executors.newCachedThreadPool();

        logger.info("Making {} concurrent random money transfers...", CONCURRENT_TRANSFERS_COUNT);
        IntStream.range(0, CONCURRENT_TRANSFERS_COUNT).forEach((i) -> CompletableFuture.runAsync(() -> {
            final TransferRequestDto transferRequestDto = createTransferRequest();
            transferController.transfer(transferRequestDto);
        }, executorService));

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);

        final BigDecimal totalAfter = StreamEx.of(accountController.getAllAccounts())
                .map(AccountDto::getAmount)
                .reduce((BigDecimal::add))
                .map(b -> b.setScale(2, RoundingMode.FLOOR))
                .get();

        logger.info("Total balance after: {}, account states:", totalAfter);
        StreamEx.of(accountController.getAllAccounts()).forEach(a -> logger.info(a.toString()));

        Assert.assertEquals(totalBefore.toString(), totalAfter.toString());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testTransferShouldFailWhenNotEnoughMoney() {
        final BigDecimal firstAccountAmount = accountController.getAccountById(1L).getAmount();
        final BigDecimal amountToTransfer = firstAccountAmount.add(new BigDecimal("1.00"));

        transferController.transfer(new TransferRequestDto(1L, 2L, amountToTransfer));
    }


    private static TransferRequestDto createTransferRequest() {
        final long firstAccountId = RANDOM.nextInt(TEST_ACCOUNT_NUMBER) + 1;

        long secondAccountId = firstAccountId;
        while (secondAccountId == firstAccountId) {
            secondAccountId = RANDOM.nextInt(TEST_ACCOUNT_NUMBER) + 1;
        }

        final BigDecimal amount = new BigDecimal(Math.random() * MAX_TRANSFER_AMOUNT).setScale(2, RoundingMode.FLOOR);

        return new TransferRequestDto(firstAccountId, secondAccountId, amount);
    }

}
