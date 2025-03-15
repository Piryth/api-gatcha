package fr.imt.combat_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@EnableMongoRepositories
@EnableFeignClients
@SpringBootApplication
public class CombatApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CombatApiApplication.class, args);
    }
}


