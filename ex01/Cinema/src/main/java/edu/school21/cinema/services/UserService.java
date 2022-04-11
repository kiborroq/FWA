package edu.school21.cinema.services;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		validate(email, firstName, lastName, password);

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
		validate(email, password);

		User user = userRepository.findUserByEmail(email);
		return user != null && encoder.matches(password, user.getPassword()) ? user : null;
	}


	private void validate(String email, String password) {
		Map<String, String> errors = new HashMap<>();

		if (StringUtils.isEmpty(email)) {
			errors.put("email", "Not defined");
		}

		if (StringUtils.isEmpty(password)) {
			errors.put("password", "Not defined");
		}

		if (!errors.isEmpty()) {
			throw new FwaRuntimeException(errors);
		}
	}

	private void validate(String email, String firstName, String lastName, String password) {
		Map<String, String> errors = new HashMap<>();

		if (StringUtils.isEmpty(email)) {
			errors.put("email", "Not defined");
		} else if (email.length() > User.EMAIL_LENGTH) {
			errors.put("email", "Length should be less than " + User.EMAIL_LENGTH);
		}

		if (StringUtils.isEmpty(firstName)) {
			errors.put("firstName", "Not defined");
		} else if (firstName.length() > User.NAME_LENGTH) {
			errors.put("firstName", "Length should be less than " + User.NAME_LENGTH);
		}

		if (StringUtils.isEmpty(lastName)) {
			errors.put("lastName", "Not defined");
		} else if (lastName.length() > User.NAME_LENGTH) {
			errors.put("lastName", "Length should be less than " + User.NAME_LENGTH);
		}

		if (StringUtils.isEmpty(password)) {
			errors.put("password", "Not defined");
		}

		if (!errors.isEmpty()) {
			throw new FwaRuntimeException(errors);
		}
	}
}
