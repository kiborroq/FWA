package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserSession implements Serializable {

	private static final long serialVersionUID = -8838817292282705503L;

	Long id;
	String ip;
	Long userId;
	LocalDateTime date;
}
