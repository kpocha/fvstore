package com.kpocha.fvstore.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kpocha.fvstore.api", "com.kpocha.fvstore.core.service", "com.kpocha.fvstore.core.model"}) // Escanea servicios y modelos del core también
@OpenAPIDefinition(info = @Info(title = "FVStore API", version = "1.0", description = "API para la tienda FV"))
public class FvstoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FvstoreApiApplication.class, args);
    }

}
