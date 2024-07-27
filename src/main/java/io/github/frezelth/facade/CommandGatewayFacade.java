package io.github.frezelth.facade;

import org.axonframework.commandhandling.gateway.CommandGateway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CommandGatewayFacade {

    private final CommandGateway commandGateway;

    public CommandGatewayFacade(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Transactional
    public <T> T send(Object command){
        return commandGateway.sendAndWait(command);
    }
}
