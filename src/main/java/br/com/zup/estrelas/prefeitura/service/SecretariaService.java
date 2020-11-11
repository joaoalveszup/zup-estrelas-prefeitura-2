package br.com.zup.estrelas.prefeitura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class SecretariaService implements ISecretariaService {
	private static final String SECRETARIA_EXCLUIDA_COM_SUCESSO = "Secretaria excluida com sucesso!";
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
			Secretaria secretaria = new Secretaria();
			BeanUtils.copyProperties(secretariaDTO, secretaria);
			
			repository.save(secretaria);
			return new MensagemDTO(SECRETARIA_CADASTRADA_COM_SUCESSO);
		}

		return new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
	}

	public Secretaria buscarSecretariaPorId(Long idSecretaria) {
		return repository.findById(idSecretaria).orElse(null);
	}

	public List<Secretaria> listarSecretarias() {
		return (List<Secretaria>) repository.findAll();
	}

	public MensagemDTO alterarSecretaria(Long idSecretaria, SecretariaDTO secretariaDTO) {
		Optional<Secretaria> secretariaConsultada = repository.findById(idSecretaria);
		Secretaria secretaria = repository.findByArea(secretariaDTO.getArea()).get();
		
		boolean verificaAreaOcupada = repository.findByArea(secretariaDTO.getArea()).isPresent();
		boolean verificaIdConsultadoAreaDiferenteIdEnviado = secretaria.getIdSecretaria() != idSecretaria;

		if (verificaAreaOcupada && verificaIdConsultadoAreaDiferenteIdEnviado) {
			return new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
		}
		
		if (secretariaConsultada.isPresent()) {
			BeanUtils.copyProperties(secretariaDTO, secretaria);
			
			secretaria.setIdSecretaria(idSecretaria);

			repository.save(secretaria);
			return new MensagemDTO(SECRETARIA_ALTERADA_COM_SUCESSO);
		}
		
		return new MensagemDTO(SECRETARIA_INEXISTENTE);
	}

	public MensagemDTO removerSecretaria(Long idSecretaria) {
		if (repository.existsById(idSecretaria)) {
			repository.deleteById(idSecretaria);
			return new MensagemDTO(SECRETARIA_EXCLUIDA_COM_SUCESSO);
		}

		return new MensagemDTO(SECRETARIA_INEXISTENTE);
	}

}
