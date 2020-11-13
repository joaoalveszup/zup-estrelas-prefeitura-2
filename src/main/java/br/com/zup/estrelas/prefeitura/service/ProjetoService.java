package br.com.zup.estrelas.prefeitura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.ConclusaoProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.ProjetoRepository;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class ProjetoService implements IProjetoService{
	
	private static final String CUSTO_DO_PROJETO_INVALIDO = "Não foi possivel cadastrar o projeto, valor custo do projeto invalido.";

	private static final String ORCAMENTO_PROJETO_INSUFICIENTE = "Não foi possivel cadastrar o projeto, secretaria não possui orçamento suficiente.";

	private static final String PROJETO_ADICIONADO_COM_SUCESSO = "Projeto adicionado com sucesso!";

	private static final String SECRETARIA_INEXISTENTE = "Não foi possivel cadastrar projeto, secretaria inexistente.";
	
	@Autowired
	ProjetoRepository repository;
	
	@Autowired
	SecretariaRepository secretariaRepository;
	
	public MensagemDTO adicionarProjeto(ProjetoDTO projetoDTO) {
		Optional<Secretaria> secretariaOptional = secretariaRepository.findById(projetoDTO.getIdSecretaria());

		if (secretariaOptional.isEmpty()) {
			return new MensagemDTO(SECRETARIA_INEXISTENTE);
		}
		
		Secretaria secretaria = secretariaOptional.get();
		
		boolean verificaCustoIgualInferiorZero = projetoDTO.getCusto() <= 0;
		boolean salarioCompativelOrcamentoProjetoSecretaria = projetoDTO.getCusto() <= secretaria.getOrcamentoProjeto();
			
		if(verificaCustoIgualInferiorZero) {
			return new MensagemDTO(CUSTO_DO_PROJETO_INVALIDO);
		}
		
		if(salarioCompativelOrcamentoProjetoSecretaria) {
			Projeto projeto = new Projeto();
			
			BeanUtils.copyProperties(projetoDTO, projeto);
			
			projeto.setSecretaria(secretaria);
			projeto.setDataInicio(LocalDate.now());
			
			subtrairOrcamentoProjetoSecretaria(secretaria, projeto.getCusto());
			
			repository.save(projeto);
			
			return new MensagemDTO(PROJETO_ADICIONADO_COM_SUCESSO);
		}
		
		return new MensagemDTO(ORCAMENTO_PROJETO_INSUFICIENTE);
	}

	public Projeto buscarProjetoPorId(Long idProjeto) {
		return repository.findById(idProjeto).orElse(null);
	}

	public List<Projeto> listarProjetos() {
		return(List<Projeto>) repository.findAll();
	}

	public MensagemDTO alterarProjeto(Long idProjeto, ProjetoDTO projetoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO excluirProjeto(Long idProjeto) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO concluirProjeto(Long idProjeto, ConclusaoProjetoDTO conclusaoProjetoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public void subtrairOrcamentoProjetoSecretaria(Secretaria secretaria, Double custo) {
		secretaria.setOrcamentoProjeto(secretaria.getOrcamentoProjeto() - custo);
		secretariaRepository.save(secretaria);
	}
}
