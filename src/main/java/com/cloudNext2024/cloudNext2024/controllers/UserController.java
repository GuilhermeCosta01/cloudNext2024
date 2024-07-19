package com.cloudNext2024.cloudNext2024.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

@RequestMapping("/user")  //definindo minha rota ou endereço;
@RestController           // dizendo ao springboot que essa é a classe das rotas
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/incluir")
	public ResponseEntity<String> incluir(@RequestBody @Valid UserEntity userr, BindingResult bindingResult) {		
		try {
			
			boolean isFutureDate = userr.getDateBirth().isAfter(LocalDate.now());
			
			if (isFutureDate || bindingResult.hasErrors()) {
				throw new Exception();
			}
			
			userr.setSenha(DigestUtils.md5DigestAsHex(userr.getSenha().getBytes()));
			
			UserEntity usuarioSalvo = userRepository.save(userr);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Ocorreu um erro");
		}
		
		return ResponseEntity.ok("Cadastro realizado com sucesso");
	}

	@GetMapping("/buscar/{id}")
	public Optional<UserEntity> buscar(@PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");
		}
		return userRepository.findById(id);
	}

	@PutMapping("/alterar/{id}")
	public UserEntity alterar(@RequestBody UserEntity userr, @PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		userr.setId(id);
		UserEntity usuarioAlterado = userRepository.save(userr);
		return usuarioAlterado;
	}

	@DeleteMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("Funcionário não encontrado!");

		}
		userRepository.deleteById(id);
		return "Usuario deletado com sucesso!";

	}
	@GetMapping("/listar")
	public List<UserEntity> listar() {
		return userRepository.findAll();
}
}