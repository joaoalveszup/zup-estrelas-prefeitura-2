package br.com.zup.estrelas.prefeitura.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.prefeitura.dto.ConclusaoProjetoDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.entity.Projeto;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.ProjetoRepository;
import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceTests {
	private static final String PROJETO_CONCLUIDO_COM_SUCESSO = "Projeto concluido com sucesso";
	
	private static final String INDICATIVO_CONCLUSAO_REALIZADA = "Mensagem indicativa de projeto concluido com sucesso";
	
	private static final String INDICATIVO_PROJETO_INEXISTENTE = "Mensagem indicativa de projeto não concluido, projeto inexistente";
	
	private static final String INDICATIVO_DATA_INVALIDA = "Mensagem indicativa de projeto não concluido, data fim invalida";
	
	private static final String PROJETO_INEXISTENTE = "Não foi possivel alterar projeto, projeto inexistente.";
	
	private static final String DATA_DE_CONCLUSÃO_IGUAL_OU_INFERIOR_A_DATA_DE_INICIO = "Não foi possivel concluir o projeto, data de conclusão igual ou inferior a data de inicio";
	
	@Mock
	ProjetoRepository repository;
	
	@InjectMocks
	ProjetoService service;
	
	@Test
	public void deveConcluirProjeto() {
		ConclusaoProjetoDTO conclusaoProjetoDTO = new ConclusaoProjetoDTO(LocalDate.of(2020, 12, 01));
		Secretaria secretaria = new Secretaria();
		
		Projeto projeto = new Projeto(1L, "Projeto auxilio", "Auxilio creche", 15000D, secretaria, LocalDate.now(), null, false);
		
		Mockito.when(repository.findById(projeto.getIdProjeto())).thenReturn(Optional.of(projeto));
		
		boolean verificaDataConclusaoSuperiorDataInicio = conclusaoProjetoDTO.getDataEntrega().isAfter(projeto.getDataInicio());
		
		Assert.assertEquals(verificaDataConclusaoSuperiorDataInicio, true);
		
		MensagemDTO mensagemRetornada = service.concluirProjeto(projeto.getIdProjeto(), conclusaoProjetoDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(PROJETO_CONCLUIDO_COM_SUCESSO);
		
		Assert.assertEquals(INDICATIVO_CONCLUSAO_REALIZADA, mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveConcluirProjetoInexistente() {
		ConclusaoProjetoDTO conclusaoProjetoDTO = new ConclusaoProjetoDTO(LocalDate.of(2020, 12, 01));
		
		Long idProjeto = 1L;

		Mockito.when(repository.findById(idProjeto)).thenReturn(Optional.empty());
		
		boolean projetoInexistente = true;
		
		Assert.assertEquals(projetoInexistente, true);
		
		MensagemDTO mensagemRetornada = service.concluirProjeto(idProjeto, conclusaoProjetoDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(PROJETO_INEXISTENTE);
		
		Assert.assertEquals(INDICATIVO_PROJETO_INEXISTENTE, mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveConcluirProjetoDataTerminoInferiorDataInicio() {
		ConclusaoProjetoDTO conclusaoProjetoDTO = new ConclusaoProjetoDTO(LocalDate.of(2020, 11, 01));
		Secretaria secretaria = new Secretaria();
		
		Projeto projeto = new Projeto(1L, "Projeto auxilio", "Auxilio creche", 15000D, secretaria, LocalDate.now(), null, false);
		
		Mockito.when(repository.findById(projeto.getIdProjeto())).thenReturn(Optional.of(projeto));
		
		boolean verificaDataConclusaoSuperiorDataInicio = conclusaoProjetoDTO.getDataEntrega().isAfter(projeto.getDataInicio());
		
		Assert.assertEquals(verificaDataConclusaoSuperiorDataInicio, false);
		
		MensagemDTO mensagemRetornada = service.concluirProjeto(projeto.getIdProjeto(), conclusaoProjetoDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(DATA_DE_CONCLUSÃO_IGUAL_OU_INFERIOR_A_DATA_DE_INICIO);
		
		Assert.assertEquals(INDICATIVO_DATA_INVALIDA, mensagemEsperada, mensagemRetornada);
	}
	
}
