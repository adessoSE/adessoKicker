package de.adesso.adessoKicker.services;

import java.util.ArrayList;
import java.util.List;

import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private TeamRepository teamRepository;

	@Autowired
	public UserService(UserRepository userRepository, TeamRepository teamRepository) {

		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
	}

		public List<User> getAllUsers() {

	        List<User> users = new ArrayList<>();
	        userRepository.findAll().forEach(users::add);
			return users;
		}

		public User getUserById(long id) {

		    return userRepository.findByUserId(id);
		}

		public void saveUser(User user) {

	        userRepository.save(user);
		}

		public void deleteUser(long id) {

		    userRepository.delete(userRepository.findByUserId(id));
		}
/***
		public void addTeamIdToUser(Team team, long userId) {

			User user = userRepository.findByUserId(userId);
			user.addToTeam(teamRepository.findByTeamId(team.getTeamId()));
			userRepository.save(user);

		}
***/
}
