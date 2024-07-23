package com.cloudNext2024.cloudNext2024.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private LocalDate dateBirth;
	
	//@JsonProperty(access = JsonProperty.Access.READ_ONLY) // o acesso a essa variavel só pode ser de leitura
	@NotBlank
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private Planos plan;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<FileEntity> files = new ArrayList<>();
	
}

