package br.com.zup.estrelas.prefeitura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.service.SecretariaService;

@RestController
@RequestMapping ("/secretarias")
public class SecretariaController {

	@Autowired
	SecretariaService service;
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO adicionarSecretaria(@RequestBody SecretariaDTO secretariaDTO) {
		return service.adicionarSecretaria(secretariaDTO);
	}
}
