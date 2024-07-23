package com.cloudNext2024.cloudNext2024.entities;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTOalterar {

	private String name;
	private LocalDate dateBirth;
	private String senha;
	private Planos plan;
}
