package edu.school21.cinema.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@ComponentScan(basePackages = "edu.school21.cinema")
public class ApplicationConfig {

	@Bean
	public JdbcTemplate jdbcTemplate() throws IOException {
		JdbcTemplate template = new JdbcTemplate(dataSource());

		String schema = new String(Files.readAllBytes(Paths.get("src/main/resources/sql/schema.sql")), StandardCharsets.UTF_8);
		String data = new String(Files.readAllBytes(Paths.get("src/main/resources/sql/data.sql")), StandardCharsets.UTF_8);
		template.execute(schema);
		template.execute(data);

		return template;
	}

	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig("src/main/webapp/WEB-INF/application.properties");
		return new HikariDataSource(config);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
