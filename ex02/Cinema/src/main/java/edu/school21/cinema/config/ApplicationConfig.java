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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

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
	public DataSource dataSource() throws IOException {
		Properties properties = new Properties();

		try	(FileInputStream fis = new FileInputStream("src/main/webapp/WEB-INF/application.properties")) {
			properties.load(fis);
		}

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(properties.getProperty("jdbcUrl"));
		config.setUsername(properties.getProperty("dataSource.user"));
		config.setPassword(properties.getProperty("dataSource.password"));
		config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("maximumPoolSize")));

		return new HikariDataSource(config);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
