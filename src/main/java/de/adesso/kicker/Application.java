package de.adesso.kicker;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.TournamentRepository;
import de.adesso.kicker.tournament.singleelimination.SingleElimination;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    SingleEliminationService singleEliminationService;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, TournamentRepository tournamentRepository) {
        return (args) -> {
            SingleElimination singleElimination = new SingleElimination("Test");
            User userA = new User("test1", "test", "test", "test");
            User userB = new User("test2", "test", "test2", "test");
            Team team = new Team("Test", userA, userB);
            singleElimination.addTeam(team);
            tournamentRepository.save(singleElimination);
            // System.out.println(team + " " + singleElimination);
            // singleEliminationService.addTeamToTournament(singleElimination, team);
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User user : userRepository.findAll()) {
                // user.increaseWins();
                // userRepository.save(user);
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            userRepository.findById(1L).ifPresent(customer -> {
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
            // log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}
