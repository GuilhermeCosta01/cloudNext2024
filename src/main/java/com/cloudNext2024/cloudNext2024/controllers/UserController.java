package com.cloudNext2024.cloudNext2024.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
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
	public ResponseEntity<UserEntity> incluir(@RequestBody UserEntity userr) {
		System.out.println(userr.getId());
		System.out.println(userr.getName());
		
//		Explicacao do metodo .isAfter()
//		data1.isAfter(data2)
//		se data1 for antes que data2, retorna false
//		se data1 for depois que data2, retorna true
//		se data1 for igual a data2, retorna false
		boolean isFutureDate = userr.getDateBirth().isAfter(LocalDate.now());
		
		if (isFutureDate) {
			return ResponseEntity.badRequest().body(null);
		}
		
		userr.setSenha(DigestUtils.md5DigestAsHex(userr.getSenha().getBytes()));
		
		UserEntity usuarioSalvo = userrepository.save(userr);
		return ResponseEntity.ok(usuarioSalvo);
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