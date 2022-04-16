package edu.school21.cinema.repositories;

import edu.school21.cinema.models.File;
import edu.school21.cinema.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class FileRepository {

	@Autowired
	private static JdbcTemplate jdbcTemplate;


	public static User findUserByEmail2(String email) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM user_account WHERE email = ?", new FileMapper.UserMapper(), email);
		} catch (Exception e) {
			return null;
		}
	}

	public void saveFile(File file) {
		jdbcTemplate.update("INSERT INTO file (uuid, name, mime, size, user_id)  VALUES (?, ?, ?, ?, ?)",
				file.getUuid(), file.getName(), file.getMime(), file.getSize(), file.getUser_id());
	}


	private static class FileMapper implements RowMapper<File> {


		@Override
		public File mapRow(ResultSet rs, int rowNum) throws SQLException {
			File file = new File();
			file.setId(rs.getLong(("id")));
			file.setName(rs.getString("name"));
			file.setSize(rs.getLong("size"));
			file.setMime(rs.getString("mime"));
			file.setUuid(UUID.fromString(rs.getString("uuid")));
			file.setUser_id(rs.getLong("user_id"));
			return file;
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
}
