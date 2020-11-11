package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;

public interface ISecretariaService {
	public MensagemDTO adicionarSecretaria(SecretariaDTO secretariaDTO);
	
	public Secretaria buscarSecretaria(Long idSecretaria);
	
	public MensagemDTO alterarSecretaria(Long idSecretaria, SecretariaDTO secretariaDTO);
	
	public MensagemDTO removerSecretaria(Long idSecretaria); 
	
	public List<Secretaria> buscarSecretarias();
}
