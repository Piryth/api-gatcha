package fr.imt.monster_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MonsterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonsterApiApplication.class, args);
	}

}
