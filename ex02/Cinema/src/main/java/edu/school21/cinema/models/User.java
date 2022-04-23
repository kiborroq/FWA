package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class User implements Serializable {
	private static final long serialVersionUID = -5336859537130156295L;

	public static final Integer NAME_LENGTH = 127;
	public static final Integer EMAIL_LENGTH = 320;

	private Long id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private ImageFile avatar;
}
