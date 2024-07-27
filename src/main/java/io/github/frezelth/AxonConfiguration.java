package io.github.frezelth;

import io.github.frezelth.model.commandhandler.TestAggregate;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.config.AggregateConfigurer;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.serialization.json.JacksonSerializer;

@ApplicationScoped
public class AxonConfiguration {

    private Configuration configuration;

    @Inject
    @PersistenceUnit("axon")
    private EntityManager entityManager;

    // public AxonConfiguration(EntityManager entityManager) {
    //     this.entityManager = entityManager;
    // }

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
