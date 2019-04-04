package org.example.controller;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.example.dto.TransferRequestDto;
import org.example.service.TransferService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

    @Inject
    private TransferService transferService;

    @POST
    @Timed
    public void transfer(TransferRequestDto request) {
        transferService.transfer(request.getSrcAccount(), request.getDstAccount(), request.getAmount());
    }

}
