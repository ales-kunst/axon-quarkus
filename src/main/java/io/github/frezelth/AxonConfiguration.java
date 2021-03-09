package io.github.frezelth;

import io.github.frezelth.model.commandhandler.TestAggregate;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.config.AggregateConfigurer;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.serialization.json.JacksonSerializer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

@ApplicationScoped
public class AxonConfiguration {

    private Configuration configuration;

    private final EntityManager entityManager;

    public AxonConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void configureAxon(){
        Configurer configurer = DefaultConfigurer.jpaConfiguration(
                new SimpleEntityManagerProvider(entityManager))
                .configureAggregate(
                        AggregateConfigurer.defaultConfiguration(TestAggregate.class)
                                .configureRepository(c -> EventSourcingRepository.builder(TestAggregate.class)
                                        .eventStore(c.eventStore())
                                        .build())
                )
                .configureSerializer(conf -> JacksonSerializer.builder()
                        .lenientDeserialization()
                        .build())
                .configureEventSerializer(
                        conf -> JacksonSerializer.builder()
                                .lenientDeserialization()
                                .build()
                );
        this.configuration = configurer.start();
    }

    @Produces
    @ApplicationScoped
    public CommandGateway commandGateway() {
        return configuration.commandGateway();
    }

}
