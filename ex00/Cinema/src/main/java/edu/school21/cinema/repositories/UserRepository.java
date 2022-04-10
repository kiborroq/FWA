package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User findUserByEmail(String email) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM user_account WHERE email = ?", new UserMapper(), email);
		} catch (Exception e) {
			return null;
		}
	}

	public void saveUser(User user) {
		jdbcTemplate.update("INSERT INTO user_account (first_name, last_name, email, password)  VALUES (?, ?, ?, ?)",
				user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
	}

	private static class UserMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setEmail(rs.getString("email"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setPassword(rs.getString("password"));

			return user;
		}
	}
}
