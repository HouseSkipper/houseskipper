package fr.univ.lorraine.houseSkipper;

import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HouseSkipperApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(HouseSkipperApplication.class, args);
	}



}
