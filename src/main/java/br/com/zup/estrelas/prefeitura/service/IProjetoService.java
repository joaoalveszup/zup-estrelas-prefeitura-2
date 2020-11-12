package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;

public interface IProjetoService {
	public MensagemDTO adicionarProjeto(ProjetoDTO projetoDTO);
	
	public Projeto buscarProjetoPorID(Long idProjeto);
	
	public List<Projeto> buscarProjetos();
	
	public MensagemDTO alterarProjeto(Long idProjeto, ProjetoDTO projetoDTO);
	
	public MensagemDTO excluirProjeto(Long idProjeto);

}
