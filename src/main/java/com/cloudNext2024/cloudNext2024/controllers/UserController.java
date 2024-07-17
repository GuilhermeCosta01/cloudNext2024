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

import com.cloudNext2024.cloudNext2024.entities.UserEntity;
import com.cloudNext2024.cloudNext2024.repositories.UserRepository;

@RequestMapping("/user")  //definindo minha rota ou endereço;
@RestController           // dizendo ao springboot que essa é a classe das rotas
public class UserController {

	@Autowired
	private UserRepository userrepository;

	@PostMapping("/incluir")
	public UserEntity incluir(@RequestBody UserEntity userr) {
		System.out.println(userr.getId());
		System.out.println(userr.getName());
		UserEntity usuarioSalvo = userrepository.save(userr);
		return usuarioSalvo;
	}

	@GetMapping("/buscar/{id}")
	public Optional<UserEntity> buscar(@PathVariable Long id) {
		if (!userrepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");
		}
		return userrepository.findById(id);
	}

	@PutMapping("/alterar/{id}")
	public UserEntity alterar(@RequestBody UserEntity userr, @PathVariable Long id) {
		if (!userrepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		userr.setId(id);
		UserEntity usuarioAlterado = userrepository.save(userr);
		return usuarioAlterado;
	}

	@DeleteMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		if (!userrepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		userrepository.deleteById(id);
		return "Usuario deletado com sucesso!";

	}
	@GetMapping("/listar")
	public List<UserEntity> listar() {
		return userrepository.findAll();
}
}