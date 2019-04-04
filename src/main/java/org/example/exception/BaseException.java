package org.example.exception;

import javax.ws.rs.core.Response;

public abstract class BaseException extends RuntimeException {
    private Response.Status status;
    private String message;

    public BaseException(Response.Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseException(Response.Status status) {
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
