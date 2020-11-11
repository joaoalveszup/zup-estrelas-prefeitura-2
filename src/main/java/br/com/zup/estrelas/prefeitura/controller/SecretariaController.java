package br.com.zup.estrelas.prefeitura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.service.ISecretariaService;

@RestController
@RequestMapping("/secretarias")
public class SecretariaController {

	@Autowired
	ISecretariaService service;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO adicionarSecretaria(@RequestBody SecretariaDTO secretariaDTO) {
		return service.adicionarSecretaria(secretariaDTO);
	}

	@GetMapping(path = "{idSecretaria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Secretaria buscarSecretariaPorId(@PathVariable Long idSecretaria) {
		return service.buscarSecretariaPorId(idSecretaria);	
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Secretaria> listarSecretarias() {
		return service.listarSecretarias();
	}

	@PutMapping(path = "{idSecretaria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alterarSecretaria(@PathVariable Long idSecretaria, @RequestBody SecretariaDTO secretariaDTO) {
		return service.alterarSecretaria(idSecretaria, secretariaDTO);
	}

	@DeleteMapping(path = "{idSecretaria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO removerSecretaria(@PathVariable Long idSecretaria) {
		return service.removerSecretaria(idSecretaria);
	}
}
