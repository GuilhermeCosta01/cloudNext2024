package com.cloudNext2024.cloudNext2024.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@NotBlank
	private LocalDate dateBirth;
	@Column(nullable = false, unique = true)
	
	@NotBlank
	@Email
	private String email;
	@Column(nullable = false)
	
	@NotBlank
	private String senha;
	
	private int plan;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	  private List<FileEntity> files;
	
}

