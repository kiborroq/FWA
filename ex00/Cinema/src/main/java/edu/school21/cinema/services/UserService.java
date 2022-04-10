package edu.school21.cinema.services;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;

	public void createUser(String email, String firstName, String lastName, String password) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(encoder.encode(password));

		if (userRepository.findUserByEmail(email) == null) {
			userRepository.saveUser(user);
			log.info("User {} was created", user);
		} else {
			log.error("User with email {} already exists", user.getEmail());
			Map<String, String> errors = new HashMap<>();
			errors.put("email", String.format("Email '%s' is already used", email));
			throw new FwaRuntimeException(errors);
		}
	}

	public User login(String email, String password) {
		User user = userRepository.findUserByEmail(email);
		return user != null && encoder.matches(password, user.getPassword()) ? user : null;
	}
}
