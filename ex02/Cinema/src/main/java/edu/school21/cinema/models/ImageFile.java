package edu.school21.cinema.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ImageFile implements Serializable {

	private static final long serialVersionUID = 6446253987052231822L;

	private Long id;
	private UUID uuid;
	private String name;
	private Long size;
	private String mime;
	private Long userId;
	private LocalDateTime date;
}
