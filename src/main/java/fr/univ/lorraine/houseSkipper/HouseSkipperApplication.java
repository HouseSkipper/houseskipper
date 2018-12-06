package fr.univ.lorraine.houseSkipper;

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
/*
@Bean
	ApplicationRunner init(TaskRepository repository, RoomRepository roomRepository, HouseRepository houseRepository) {
		roomRepository.deleteAll();
		houseRepository.deleteAll();
		repository.deleteAll();
		return args -> {
			Stream.of(
					new Task("X1", "X1","X1", new Date(), "En Attente"),
					new Task("X2", "X2","X2", new Date(), "En Attente"),
					new Task("X3", "X3","X3", new Date(), "En Attente"),
					new Task("X4", "X4","X4", new Date(), "En Attente")
			).forEach(task -> {
				repository.save(task);
			});
			// Query database
			repository.findAll().forEach(System.out::println);
		};
	}
 */

}
