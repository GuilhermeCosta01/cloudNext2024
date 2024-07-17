package com.cloudNext2024.cloudNext2024.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudNext2024.cloudNext2024.entities.FileEntity;
import com.cloudNext2024.cloudNext2024.repositories.FileRepository;

@RequestMapping("/file") // definindo minha rota ou endereço;
@RestController // dizendo ao springboot que essa é a classe das rotas
public class FileController {

	@Autowired
	private FileRepository filerepository;

	@PostMapping("/incluir")
	public FileEntity incluir(@RequestBody FileEntity filee) {
		System.out.println(filee.getFileId());
		System.out.println(filee.getName());
		FileEntity usuarioSalvo = filerepository.save(filee);
		return usuarioSalvo;
	}

	@GetMapping("/buscar/{id}")
	public Optional<FileEntity> buscar(@PathVariable Long id) {
		if (!filerepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");
		}
		return filerepository.findById(id);
	}

	@PutMapping("/alterar/{id}")
	public FileEntity alterar(@RequestBody FileEntity userr, @PathVariable Long id) {
		if (!filerepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		userr.setFileId(id);
		FileEntity usuarioAlterado = filerepository.save(userr);
		return usuarioAlterado;
	}

	@DeleteMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		if (!filerepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		filerepository.deleteById(id);
		return "Usuario deletado com sucesso!";

	}

	@GetMapping("/listar")
	public List<FileEntity> listar() {
		return filerepository.findAll();
	}
}
