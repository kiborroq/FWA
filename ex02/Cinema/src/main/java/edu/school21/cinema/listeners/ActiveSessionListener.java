package edu.school21.cinema.listeners;

import lombok.Value;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebListener
public class ActiveSessionListener implements HttpSessionListener {

	private static final Map<String, HttpSession> ACTIVE_SESSIONS = new HashMap<>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setMaxInactiveInterval(60);
		ACTIVE_SESSIONS.put(session.getId(), session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ACTIVE_SESSIONS.remove(se.getSession().getId(), se.getSession());
	}

	public static List<SessionInfo> getActiveSessions() {
		return ACTIVE_SESSIONS.values().stream().map(session -> {
			LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(session.getCreationTime()), ZoneId.systemDefault());

			return new SessionInfo(
					session.getAttribute("ip").toString(),
					dateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
					dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
		}).collect(Collectors.toList());
	}

	public static void addActiveSession(HttpSession session) {
		ACTIVE_SESSIONS.put(session.getId(), session);
	}

	@Value
	public static class SessionInfo {
		String ip;
		String date;
		String time;
	}
}
