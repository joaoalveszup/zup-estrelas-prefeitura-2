package br.com.zup.estrelas.prefeitura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.prefeitura.dto.AlterarProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.ConclusaoProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;
import br.com.zup.estrelas.prefeitura.service.IProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	IProjetoService service;
	
	//Passar ID Secretaria no URL
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO adicionarProjeto(@RequestBody ProjetoDTO projetoDTO) {
		return service.adicionarProjeto(projetoDTO);
	}

	@GetMapping(path = "/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Projeto buscarProjetoPorId(@PathVariable Long idProjeto) {
		return service.buscarProjetoPorId(idProjeto);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Projeto> listarProjetos() {
		return service.listarProjetos();
	}

	@PutMapping(path = "/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alterarProjeto(@PathVariable Long idProjeto, @RequestBody AlterarProjetoDTO alterarProjetoDTO) {
		return service.alterarProjeto(idProjeto, alterarProjetoDTO);
	}

	@PutMapping(path = "conclusoes/{idProjeto}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO concluirProjeto(@PathVariable Long idProjeto, @RequestBody ConclusaoProjetoDTO conclusaoProjetoDTO) {
		return service.concluirProjeto(idProjeto, conclusaoProjetoDTO);
	}
}
