package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class File {

	private Long id;
	private UUID uuid;
	private String name;
	private Long size;
	private String mime;
}
