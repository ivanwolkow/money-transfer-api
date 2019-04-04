package org.example.controller;

import com.google.inject.Inject;
import org.example.dto.AccountDto;
import org.example.service.AccountService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    private AccountService accountService;

    @GET
    @Path("/{id}")
    public AccountDto getAccountById(@PathParam("id") Long id) {
        return accountService.getAccountById(id);
    }

    @GET
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
