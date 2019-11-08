package br.com.v3s;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.v3s.authentication.model.Role;
import br.com.v3s.authentication.repository.RoleRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {
		return args -> {
			Role role = roleRepository.findByRole("ADMIN");
			if(role == null) {
				role = new Role();
				role.setRole("ADMIN");
				roleRepository.save(role);
			}
		};
	}
}
