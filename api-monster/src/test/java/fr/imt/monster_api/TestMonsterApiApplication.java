package fr.imt.monster_api;

import org.springframework.boot.SpringApplication;

public class TestMonsterApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MonsterApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
