package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.ConclusaoProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;
import br.com.zup.estrelas.prefeitura.repository.ProjetoRepository;

@Service
public class ProjetoService implements IProjetoService{

	@Autowired
	ProjetoRepository repository;
	
	public MensagemDTO adicionarProjeto(ProjetoDTO projetoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public Projeto buscarProjetoPorId(Long idProjeto) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Projeto> buscarProjetos() {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO alterarProjeto(Long idProjeto, ProjetoDTO projetoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO excluirProjeto(Long idProjeto) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO concluirProjeto(ConclusaoProjetoDTO conclusaoProjetoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
