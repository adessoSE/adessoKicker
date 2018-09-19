package de.adesso.adessoKicker;

import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // save a couple of customers
            //List<Team> teams = new ArrayList<>();
            //User user1 = new User("Peter", "Meier", "Test@mail.com");
            //userRepository.save(user1);
            //User user2 = new User("Hans", "Hans", "test@mail2.com");
            //userRepository.save(user2);
            //Team team = new Team("Test Team", userRepository.findByUserId(1L), userRepository.findByUserId(2L));
            //Date date = new Date();
            //Tournament tournament1 = new Tournament("Test Tournament", date, "Last Man Standing");
            //teams.add(team);
            //userRepository.save(new User("Jack", "Bauer", "jack.bauer@testmail.com"));
            //tournamentRepository.save(tournament1);
            //tournamentService.addTeamToTournament(tournament1, team);


            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User user : userRepository.findAll()) {
                //user.increaseWins();
                //userRepository.save(user);
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            userRepository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            userRepository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}
