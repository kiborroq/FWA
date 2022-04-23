package edu.school21.cinema.repositories;

import edu.school21.cinema.models.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SessionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Long save(UserSession session) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		insert.withTableName("session");
		insert.usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ip", session.getIp());
		parameters.put("date", session.getDate());
		parameters.put("user_id", session.getUserId());

		return (Long) insert.executeAndReturnKey(parameters);
	}

	public List<UserSession> findAllByUserId(Long userId) {
		try {
			return jdbcTemplate.query("SELECT * FROM session WHERE user_id = ? ORDER BY date DESC ", new SessionMapper(), userId);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	private static class SessionMapper implements RowMapper<UserSession> {

		@Override
		public UserSession mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserSession userSession = new UserSession();
			userSession.setId(rs.getLong("id"));
			userSession.setUserId(rs.getLong("user_id"));
			userSession.setIp(rs.getString("ip"));
			userSession.setDate(rs.getTimestamp("date").toLocalDateTime());
			return userSession;
		}
	}
}
