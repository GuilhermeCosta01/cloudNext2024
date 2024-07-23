package com.cloudNext2024.cloudNext2024.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudNext2024.cloudNext2024.entities.FileEntity;
import com.cloudNext2024.cloudNext2024.repositories.FileRepository;

@RequestMapping("/file") // definindo minha rota ou endereço;
@RestController // dizendo ao springboot que essa é a classe das rotas
public class FileController {

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private FileRepository filerepository;

	@ExceptionHandler(RuntimeException.class) // todo RuntimeException esse cara será chamado
	public ResponseEntity<String> capturaResposta(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@PostMapping("/incluir")
	public FileEntity incluir(@RequestBody FileEntity filee) {
		
		FileEntity arquivoSalvo = filerepository.save(filee);
		
		return arquivoSalvo;
	}

	@GetMapping("/buscar/{id}")
	public ResponseEntity<FileEntity> buscar(@PathVariable Long id) {
	
		return filerepository.findById(id).map(ResponseEntity::ok)
				// .map(user-> ResponseEntity.ok(user) )
				.orElse(ResponseEntity.notFound().build());
	}

//	@PutMapping("/alterar/{id}")
//	public FileEntity alterar(@RequestBody FileEntity userr, @PathVariable Long id) {
//		if (!filerepository.existsById(id)) {
//			throw new RuntimeException("Funcionário não encontrado!");
//
//		}
//		userr.setFileId(id);
//		FileEntity usuarioAlterado = filerepository.save(userr);
//		return usuarioAlterado;
//	}

	@DeleteMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		if (!filerepository.existsById(id)) {
			throw new RuntimeException("Arquivo não encontrado!");

		}
		filerepository.deleteById(id);
		return "Arquivo deletado com sucesso!";

	}

	@GetMapping("/listar")
	public List<FileEntity> listar() {
		return filerepository.findAll();
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestBody String base64String, @RequestBody String fileName)  {
//		try {
		
			boolean encodedFile = s3Service.uploadFile(base64String, fileName);
			return ResponseEntity.ok("Arquivo carregado com sucesso: " + encodedFile);
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao carregar o arquivo: " + e.getMessage());
//		}
	}

	@GetMapping("/download/{key}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String key) {
		try {
			byte[] fileBytes = s3Service.downloadFile(key);
			return ResponseEntity.ok(fileBytes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
