package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.entity.Funcionario;

public interface IFuncionarioService {
	public MensagemDTO adicionarFuncionario(FuncionarioDTO funcionarioDTO);

	public Funcionario buscarFuncionarioPorId(Long idFuncionario);

	public List<Funcionario> listarFuncionarios();

	public MensagemDTO alterarFuncionario(FuncionarioDTO funcionarioDTO);

	public MensagemDTO removerFuncionario(Long idFuncionario);
}
