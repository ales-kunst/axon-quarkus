package io.github.frezelth.api.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class EditTestAggregate {

    @TargetAggregateIdentifier
    String id;

    String name;

}
