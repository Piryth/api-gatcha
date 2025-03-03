package fr.imt.player_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlayerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerApiApplication.class, args);
	}

}
