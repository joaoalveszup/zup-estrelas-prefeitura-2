package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;

public interface ISecretariaService {
	public MensagemDTO adicionarSecretaria(SecretariaDTO secretariaDTO);
	
	public List<Secretaria> buscarSecretarias();
	
	public MensagemDTO alterarSecretaria(Long idSecretaria, SecretariaDTO secretariaDTO);
	
	public MensagemDTO removerSecretaria(Long idSecretaria); 
}
