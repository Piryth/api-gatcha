package fr.imt.monster_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@EnableMongoRepositories
@SpringBootApplication
public class MonsterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonsterApiApplication.class, args);
	}

}
