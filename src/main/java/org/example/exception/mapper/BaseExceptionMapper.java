package org.example.exception.mapper;

import org.example.exception.BaseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseExceptionMapper implements ExceptionMapper<BaseException> {
    @Override
    public Response toResponse(BaseException e) {
        return Response.status(e.getStatus())
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
