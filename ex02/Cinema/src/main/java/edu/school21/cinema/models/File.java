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
	private UUID uuid; // вставить
	private String name; // имя самого файла
	private Long size; // из файла в разных загловках
	private String mime; // тип файла
	private Long user_id; //  именно айди юзера вытаскиваем
}
