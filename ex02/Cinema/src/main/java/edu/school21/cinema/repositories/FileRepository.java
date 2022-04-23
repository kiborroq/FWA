package edu.school21.cinema.repositories;

import edu.school21.cinema.models.ImageFile;
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
import java.util.UUID;

@Repository
public class FileRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Long saveFile(ImageFile imageFile) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		insert.withTableName("file");
		insert.usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", imageFile.getName());
		parameters.put("mime", imageFile.getMime());
		parameters.put("size", imageFile.getSize());
		parameters.put("user_id", imageFile.getUserId());
		parameters.put("uuid", imageFile.getUuid());
		parameters.put("date", imageFile.getDate());

		return (Long) insert.executeAndReturnKey(parameters);
	}

	public ImageFile findFileById(Long id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM file WHERE id = ?", new FileMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<ImageFile> findAllByUserId(Long userId) {
		try {
			return jdbcTemplate.query("SELECT * FROM file WHERE user_id = ? ORDER BY date DESC ", new FileMapper(), userId);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public ImageFile findFileByUuid(UUID uuid) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM file WHERE uuid = ?", new FileMapper(), uuid);
		} catch (Exception e) {
			return null;
		}
	}

	private static class FileMapper implements RowMapper<ImageFile> {

		@Override
		public ImageFile mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImageFile imageFile = new ImageFile();
			imageFile.setId(rs.getLong(("id")));
			imageFile.setName(rs.getString("name"));
			imageFile.setSize(rs.getLong("size"));
			imageFile.setMime(rs.getString("mime"));
			imageFile.setUserId(rs.getLong("user_id"));
			imageFile.setUuid(UUID.fromString(rs.getString("uuid")));
			imageFile.setDate(rs.getTimestamp("date").toLocalDateTime());

			return imageFile;
		}
	}
}
