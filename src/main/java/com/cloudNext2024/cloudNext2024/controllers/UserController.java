package com.cloudNext2024.cloudNext2024.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudNext2024.cloudNext2024.entities.UserDTOalterar;
import com.cloudNext2024.cloudNext2024.entities.UserEntity;
import com.cloudNext2024.cloudNext2024.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	

	// capturando erro do tipo RuntimeException e transformando num tipo badRequest "erro 400" e mostrando mensagem. 
	// função e.getMessege
	@ExceptionHandler(RuntimeException.class) // todo RuntimeException esse cara será chamado
	public ResponseEntity<String> capturaResposta(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());

	}

	@PostMapping("/incluir")
	public ResponseEntity<UserEntity> incluir(@RequestBody @Valid UserEntity userr) { // , BindingResult bindingResult

		boolean isFutureDate = userr.getDateBirth().isAfter(LocalDate.now());

		if (isFutureDate) {
			throw new RuntimeException("A data não pode ser superior a de hoje!");
		}

		userr.setSenha(DigestUtils.md5DigestAsHex(userr.getSenha().getBytes()));  

		UserEntity usuarioSalvo = userRepository.save(userr);

		return ResponseEntity.ok(usuarioSalvo);
	}

	@GetMapping("/buscar/{id}")
	public ResponseEntity<UserEntity> buscar(@PathVariable Long id) {

		return userRepository.findById(id)

				.map(ResponseEntity::ok)
				// .map(user-> ResponseEntity.ok(user) )
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	
	@GetMapping("/buscarEmail/{email}")
	public ResponseEntity<UserEntity> buscarPorEmail(@PathVariable String email){
		UserEntity user = userRepository.findByEmail(email);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		}
		else {
//			throw new RuntimeException("Usuário não encontrado com o email: " + email);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
		}
		
	}


	@PutMapping("/alterar/{id}")
	public ResponseEntity<UserEntity> alterar(@RequestBody UserDTOalterar userDTO, @PathVariable Long id) {
//		if (!userRepository.existsById(id)) {
//			throw new RuntimeException("Funcionário não encontrado!");
//		}
		UserEntity userr = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Funcionário não encontrado!"));
		if(userDTO.getName()!= null) {
			userr.setName(userDTO.getName());
		}
		if(userDTO.getDateBirth()!=null) {
		userr.setDateBirth(userDTO.getDateBirth());
		}
		if(userDTO.getSenha()!=null) {
		userr.setSenha(DigestUtils.md5DigestAsHex(userDTO.getSenha().getBytes()));
		}
		if(userDTO.getPlan()!=null) {
		userr.setPlan(userDTO.getPlan());
		}
	
		UserEntity usuarioAlterado = userRepository.save(userr);

		return ResponseEntity.ok(usuarioAlterado);
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity <String> deletar(@PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");

		}
		userRepository.deleteById(id);
		return ResponseEntity.ok("Usuario deletado com sucesso!");

	}

	@GetMapping("/listar")
	public List<UserEntity> listar() {
		return userRepository.findAll();
	}
	
}
