package edu.school21.cinema.services;

import edu.school21.cinema.models.User;
import edu.school21.cinema.models.UserSession;
import edu.school21.cinema.repositories.SessionRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

	@Autowired
	private SessionRepository sessionRepository;

	public void createSession(User user, String ip) {
		UserSession userSession = new UserSession();
		userSession.setDate(LocalDateTime.now());
		userSession.setUserId(user.getId());
		userSession.setIp(ip);
		sessionRepository.save(userSession);
	}

	public List<SessionDto> getSessions(User user) {
		return sessionRepository.findAllByUserId(user.getId())
				.stream()
				.map(SessionDto::new)
				.collect(Collectors.toList());
	}

	@Value
	public static class SessionDto implements Serializable {
		private static final long serialVersionUID = -5499706925769738639L;

		String date;
		String time;
		String ip;

		public SessionDto(UserSession userSession) {
			this.date = userSession.getDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
			this.time = userSession.getDate().format(DateTimeFormatter.ofPattern("HH:mm"));
			this.ip = userSession.getIp();
		}
	}
}
