package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private Long id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
}
