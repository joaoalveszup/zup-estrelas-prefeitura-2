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

import br.com.zup.estrelas.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.entity.Funcionario;
import br.com.zup.estrelas.prefeitura.service.IFuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
	@Autowired
	IFuncionarioService service;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO adicionarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
		return service.adicionarFuncionario(funcionarioDTO);
	}

	@GetMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Funcionario buscarFuncionarioPorId(@PathVariable Long idFuncionario) {
		return service.buscarFuncionarioPorId(idFuncionario);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Funcionario> listarFuncionarios() {
		return service.listarFuncionarios();
	}

	@PutMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alterarFuncionario(@PathVariable Long idFuncionario,
			@RequestBody FuncionarioDTO funcionarioDTO) {
		return service.alterarFuncionario(idFuncionario, funcionarioDTO);
	}
	
	@DeleteMapping(path = "/{idFuncionario}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO removerFuncionario(Long idFuncionario) {
		return service.removerFuncionario(idFuncionario);
	}
}
