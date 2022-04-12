package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	public static final Integer NAME_LENGTH = 127;
	public static final Integer EMAIL_LENGTH = 320;

	private Long id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
}
