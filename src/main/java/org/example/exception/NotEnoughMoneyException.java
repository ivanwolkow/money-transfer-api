package org.example.exception;

import javax.ws.rs.core.Response;

public class NotEnoughMoneyException extends BaseException {

    public NotEnoughMoneyException() {
        super(Response.Status.INTERNAL_SERVER_ERROR, "Not enough money");
    }
}
