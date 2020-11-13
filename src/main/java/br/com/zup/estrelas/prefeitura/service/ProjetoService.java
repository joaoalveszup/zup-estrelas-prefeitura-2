package br.com.zup.estrelas.prefeitura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.AlterarProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.ConclusaoProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.ProjetoDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.ProjetoRepository;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class ProjetoService implements IProjetoService{

	private static final String DATA_DE_CONCLUSÃO_IGUAL_OU_INFERIOR_A_DATA_DE_INICIO = "Não foi possivel concluir o projeto, data de conclusão igual ou inferior a data de inicio";

	private static final String PROJETO_CONCLUIDO_COM_SUCESSO = "Projeto concluido com sucesso";

	private static final String PROJETO_ALTERADO_COM_SUCESSO = "Projeto alterado com sucesso!";

	private static final String PROJETO_INEXISTENTE = "Não foi possivel alterar projeto, projeto inexistente.";

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

	public MensagemDTO alterarProjeto(Long idProjeto, AlterarProjetoDTO alterarProjetoDTO) {		
		Optional<Projeto> projetoConsultadoOptional = repository.findById(idProjeto);
		
		if(projetoConsultadoOptional.isEmpty()) {
			return new MensagemDTO(PROJETO_INEXISTENTE);
		}

		Projeto projeto = projetoConsultadoOptional.get();
		
		projeto.setDescricao(alterarProjetoDTO.getDescricao());
		
		repository.save(projeto);
		
		return new MensagemDTO(PROJETO_ALTERADO_COM_SUCESSO);
	}

	public MensagemDTO concluirProjeto(Long idProjeto, ConclusaoProjetoDTO conclusaoProjetoDTO) {
		Optional<Projeto> projetoConsultadoOptional = repository.findById(idProjeto);
		
		if(projetoConsultadoOptional.isEmpty()) {
			return new MensagemDTO(PROJETO_INEXISTENTE);
		}

		Projeto projeto = projetoConsultadoOptional.get();
		boolean verificaDataConclusaoSuperiorDataInicio = conclusaoProjetoDTO.getDataEntrega().isAfter(projeto.getDataInicio());
		
		if(verificaDataConclusaoSuperiorDataInicio) {
			projeto.setDataEntrega(conclusaoProjetoDTO.getDataEntrega());
			projeto.setConcluido(true);
			repository.save(projeto);
			
			return new MensagemDTO(PROJETO_CONCLUIDO_COM_SUCESSO);
		}	
	
		return new MensagemDTO(DATA_DE_CONCLUSÃO_IGUAL_OU_INFERIOR_A_DATA_DE_INICIO);
	}

	public void subtrairOrcamentoProjetoSecretaria(Secretaria secretaria, Double custo) {
		secretaria.setOrcamentoProjeto(secretaria.getOrcamentoProjeto() - custo);
		secretariaRepository.save(secretaria);
	}
}
