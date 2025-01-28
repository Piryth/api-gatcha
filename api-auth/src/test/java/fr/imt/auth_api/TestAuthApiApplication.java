package fr.imt.auth_api;

import org.springframework.boot.SpringApplication;

public class TestAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(AuthApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
