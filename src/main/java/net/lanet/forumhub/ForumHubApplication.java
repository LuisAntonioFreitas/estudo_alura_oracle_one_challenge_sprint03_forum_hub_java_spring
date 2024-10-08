package net.lanet.forumhub;

import net.lanet.forumhub.infra.utilities.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "net.lanet.forumhub")
public class ForumHubApplication {
	@Autowired
	private ApplicationProperties ap; //Status

	public static void main(String[] args) {
		SpringApplication.run(ForumHubApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Status
			ap.status("console");
		};
	}

}
