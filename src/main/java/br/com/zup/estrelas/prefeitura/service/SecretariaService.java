package br.com.zup.estrelas.prefeitura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class SecretariaService implements ISecretariaService {

	private static final String SECRETARIA_INEXISTENTE = "Secretaria inexistente.";

	private static final String SECRETARIA_ALTERADA_COM_SUCESSO = "Secretaria alterada com sucesso!";

	private static final String SECRETARIA_COM_AREA_EXISTENTE = "Infelizmente não foi possivel completar a "
			+ "operação, já possui uma secretaria nesta area.";

	private static final String SECRETARIA_CADASTRADA_COM_SUCESSO = "Secretaria cadastrada com sucesso!";

	@Autowired
	SecretariaRepository repository;

	public MensagemDTO adicionarSecretaria(SecretariaDTO secretariaDTO) {
		boolean verificaDisponibilidadeArea = repository.findByArea(secretariaDTO.getArea()).isEmpty();

		if (verificaDisponibilidadeArea) {
			Secretaria secretaria = montarObjetoSecretaria(secretariaDTO);
			repository.save(secretaria);
			return new MensagemDTO(SECRETARIA_CADASTRADA_COM_SUCESSO);
		}

		return new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
	}
	
	public List<Secretaria> buscarSecretarias() {
		return (List<Secretaria>) repository.findAll();
	}

	public MensagemDTO alterarSecretaria(Long idSecretaria, SecretariaDTO secretariaDTO) {
		Optional<Secretaria> secretariaConsultada = repository.findById(idSecretaria);
		
		boolean verificaAreaOcupada = repository.findByArea(secretariaDTO.getArea()).isPresent();
		
		if(verificaAreaOcupada) {
			return new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
		}
		
		if(secretariaConsultada.isPresent()) {
			Secretaria secretaria = secretariaConsultada.get();
			secretaria = montarObjetoSecretaria(secretariaDTO);
			
			secretaria.setIdSecretaria(idSecretaria);
			
			repository.save(secretaria);
			return new MensagemDTO(SECRETARIA_ALTERADA_COM_SUCESSO);
		}
		
		return new MensagemDTO(SECRETARIA_INEXISTENTE);
	}

	public MensagemDTO removerSecretaria(Long idSecretaria) {
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
