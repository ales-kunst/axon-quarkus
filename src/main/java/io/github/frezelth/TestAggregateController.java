package io.github.frezelth;

import io.github.frezelth.api.command.CreateTestAggregate;
import io.github.frezelth.api.command.EditTestAggregate;
import io.github.frezelth.facade.CommandGatewayFacade;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/test")
public class TestAggregateController {

    private final CommandGatewayFacade commandGatewayFacade;

    public TestAggregateController(CommandGatewayFacade commandGatewayFacade) {
        this.commandGatewayFacade = commandGatewayFacade;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return commandGatewayFacade.send(new CreateTestAggregate(UUID.randomUUID().toString()));
    }

    @PATCH
    @Path(("/{id}"))
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void patch(@PathParam("id") String id) {
        commandGatewayFacade.send(new EditTestAggregate(id, "thomas"));
    }
}