package br.com.zup.estrelas.prefeitura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class SecretariaService implements ISecretariaService {

	private static final String SECRETARIA_COM_AREA_EXISTENTE = "O cadastro não ocorreu, já possui uma secretaria com esta area.";

	private static final String SECRETARIA_CADASTRADA_COM_SUCESSO = "Secretaria cadastrada com sucesso!";

	@Autowired
	SecretariaRepository repository;

	public MensagemDTO adicionarSecretaria(SecretariaDTO secretariaDTO) {
		boolean verificaAreaSecretariaNaoCadastrada = repository.findByArea(secretariaDTO.getArea()).isEmpty();

		if (verificaAreaSecretariaNaoCadastrada) {
			Secretaria secretaria = montarObjetoSecretaria(secretariaDTO);
			repository.save(secretaria);
			return new MensagemDTO(SECRETARIA_CADASTRADA_COM_SUCESSO);
		}

		return new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
	}

	public Secretaria buscarSecretaria(Long idSecretaria) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO alterarSecretaria(Long idSecretaria, SecretariaDTO secretariaDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO removerSecretaria(Long idSecretaria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Secretaria> buscarSecretarias() {
		// TODO Auto-generated method stub
		return null;
	}

	public Secretaria montarObjetoSecretaria(SecretariaDTO secretariaDTO) {
		Secretaria secretaria = new Secretaria();

		secretaria.setArea(secretariaDTO.getArea());
		secretaria.setOrcamentoProjeto(secretariaDTO.getOrcamentoProjeto());
		secretaria.setOrcamentoFolha(secretariaDTO.getOrcamentoFolha());
		secretaria.setTelefone(secretariaDTO.getTelefone());
		secretaria.setEndereco(secretariaDTO.getEndereco());
		secretaria.setSite(secretariaDTO.getSite());
		secretaria.setEmail(secretariaDTO.getEmail());

		return secretaria;
	}
}
