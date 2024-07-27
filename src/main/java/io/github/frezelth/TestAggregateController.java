package io.github.frezelth;

import io.github.frezelth.api.command.CreateTestAggregate;
import io.github.frezelth.api.command.EditTestAggregate;
import io.github.frezelth.facade.CommandGatewayFacade;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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