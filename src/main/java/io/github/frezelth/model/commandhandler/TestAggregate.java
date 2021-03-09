package io.github.frezelth.model.commandhandler;

import io.github.frezelth.api.command.CreateTestAggregate;
import io.github.frezelth.api.command.EditTestAggregate;
import io.github.frezelth.api.event.TestAggregateCreated;
import io.github.frezelth.api.event.TestAggregateEdited;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateRoot;

@AggregateRoot(type = "testAggregate")
public class TestAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    public TestAggregate(){

    }

    @CommandHandler
    public TestAggregate(CreateTestAggregate command) {
        AggregateLifecycle.apply(new TestAggregateCreated(command.getId()));
    }

    @EventSourcingHandler
    public void on(TestAggregateCreated event){
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(EditTestAggregate command){
        AggregateLifecycle.apply(new TestAggregateEdited(this.id, command.getName()));
    }

    @EventSourcingHandler
    public void on(TestAggregateEdited event){
        this.name = event.getName();
    }
}
