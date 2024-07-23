package com.cloudNext2024.cloudNext2024.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fileId;
	private String fileName;
	private double size;
	private String base64File;
	
	@ManyToOne // many(Task) to(para) one(TodoList)
	private UserEntity user;
}

