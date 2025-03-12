package fr.imt.invoc_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@EnableMongoRepositories
@EnableFeignClients
@SpringBootApplication
public class InvocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvocationApiApplication.class, args);
	}

}
