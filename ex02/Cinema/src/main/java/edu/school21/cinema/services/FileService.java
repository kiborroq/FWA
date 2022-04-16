package edu.school21.cinema.services;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.listeners.ActiveSessionListener;
import edu.school21.cinema.models.File;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.FileRepository;
import edu.school21.cinema.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private PasswordEncoder encoder;

	public String createFile(InputStream fileInputStream, Part filePart, User user) throws IOException {

		File uploadedFile = new File();
		Date date = new Date();

		uploadedFile.setUuid(UUID.fromString(filePart.getName() + date.toString()));
		java.io.File fileToSave = new java.io.File("resources/images/" + uploadedFile.getUuid());
		Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

		uploadedFile.setName(filePart.getName());
		uploadedFile.setSize(filePart.getSize());
		uploadedFile.setMime(filePart.getContentType());
		uploadedFile.setUser_id(user.getId());

		return fileToSave.toString();
	}

//	public User login(String email, String password) {
//		validate(email, password);
//
//		User user = fileRepository.findUserByEmail2(email);
//		return user != null && encoder.matches(password, user.getPassword()) ? user : null;
//	}

	public static String getURL(File file) {
		String fileUrl = "http://localhost:8080/images/" + file.getUuid();
		return fileUrl;
	}

	private void validateFile(String mime, long size) {
		Map<String, String> errors = new HashMap<>();

		if (StringUtils.isEmpty(mime) || !mime.equalsIgnoreCase("jpg") ||
				!mime.equalsIgnoreCase("png")) {
			errors.put("mime", "Not supported image format or not an image");
		}

		if (size <= 0) {
			errors.put("size", "Not A VALID SIZE");
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
