package com.facilvirtual.fvstores.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// We need to scan for components, entities, and repositories that will be in fvstores-core (once code is moved)
// The original packages from fvstoresdesk are com.facilvirtual.fvstoresdesk.*
// Assuming services will be in com.facilvirtual.fvstoresdesk.service,
// entities in com.facilvirtual.fvstoresdesk.model,
// DAOs in com.facilvirtual.fvstoresdesk.dao.
// Configuration like JpaConfig might be in com.facilvirtual.fvstoresdesk.config
@ComponentScan(basePackages = {
        "com.facilvirtual.fvstores.api", // API specific components
        "com.facilvirtual.fvstoresdesk.service", // Core services
        "com.facilvirtual.fvstoresdesk.config", // Core configuration (like JpaConfig)
        "com.facilvirtual.fvstoresdesk.persistence" // For DatabaseUpdater if it's a component
})
@EntityScan(basePackages = {
        "com.facilvirtual.fvstoresdesk.model" // Core entities
})
@EnableJpaRepositories(basePackages = {
        "com.facilvirtual.fvstoresdesk.dao" // Core DAOs
})
public class FvStoresApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FvStoresApiApplication.class, args);
    }
}
