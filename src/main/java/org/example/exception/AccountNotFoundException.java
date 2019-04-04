package org.example.exception;

import javax.ws.rs.core.Response;

public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(Long id) {
        super(Response.Status.NOT_FOUND, "Account with id=" + id + " not found");
    }
}
