package br.com.zup.estrelas.prefeitura.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.dto.SecretariaDTO;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.enums.Area;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@RunWith(MockitoJUnitRunner.class)
public class SecretariaServiceTests {

	private static final String SECRETARIA_COM_AREA_EXISTENTE = "Infelizmente não foi possivel completar a operação, já possui uma secretaria nesta area.";

	private static final String INDICATIVO_CADASTRO_REALIZADO = "Mensagem indicativa de cadastro realizado";
	
	private static final String INDICATIVO_CADASTRO_NAO_REALIZADO = "Mensagem indicativa de cadastro não realizado";
	
	private static final String INDICATIVO_REMOCAO_REALIZADA = "Mensagem indicativa de remoção realizada";
	
	private static final String INDICATIVO_REMOCAO_NAO_REALIZADA = "Mensagem indicativa de remoção não realizada";

	private static final String SECRETARIA_CADASTRADA_COM_SUCESSO = "Secretaria cadastrada com sucesso!";
	
	private static final String SECRETARIA_EXCLUIDA_COM_SUCESSO = "Secretaria excluida com sucesso!";
	
	private static final String SECRETARIA_INEXISTENTE = "Secretaria inexistente.";

	@Mock
	SecretariaRepository repository;
	
	@InjectMocks
	SecretariaService service;
	
	@Test
	public void deveRealizarCadastroSecretaria() {
		SecretariaDTO secretariaDTO = montarObjeto();

		Mockito.when(repository.findByArea(secretariaDTO.getArea())).thenReturn(Optional.empty());
		
		boolean verificaDisponibilidadeArea = true;
		Assert.assertEquals(verificaDisponibilidadeArea, true);
		
				
		MensagemDTO mensagemRetornada = service.adicionarSecretaria(secretariaDTO); 
		MensagemDTO mensagemEsperada = new MensagemDTO(SECRETARIA_CADASTRADA_COM_SUCESSO);
		
		Assert.assertEquals(INDICATIVO_CADASTRO_REALIZADO, mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveRealizarCadastroSecretaria() {
		SecretariaDTO secretariaDTO = montarObjeto();
		Secretaria secretaria = new Secretaria();
		
		BeanUtils.copyProperties(secretariaDTO, secretaria);
		secretaria.setIdSecretaria(1L);
		
		Mockito.when(repository.findByArea(secretariaDTO.getArea())).thenReturn(Optional.of(secretaria));
		
		boolean verificaDisponibilidadeArea = false;
		Assert.assertEquals(verificaDisponibilidadeArea, false);
		
		MensagemDTO mensagemRetornada = service.adicionarSecretaria(secretariaDTO); 
		MensagemDTO mensagemEsperada = new MensagemDTO(SECRETARIA_COM_AREA_EXISTENTE);
		
		Assert.assertEquals(INDICATIVO_CADASTRO_NAO_REALIZADO, mensagemEsperada, mensagemRetornada);
	}
	
	@Test 
	public void deveRemoverSecretaria() {
		SecretariaDTO secretariaDTO = montarObjeto();
		Secretaria secretaria = new Secretaria();
		
		BeanUtils.copyProperties(secretariaDTO, secretaria);
		secretaria.setIdSecretaria(1L);
		
		Mockito.when(repository.existsById(secretaria.getIdSecretaria())).thenReturn(true);
		
		MensagemDTO mensagemRetornada = service.removerSecretaria(secretaria.getIdSecretaria());
		MensagemDTO mensagemEsperada = new MensagemDTO(SECRETARIA_EXCLUIDA_COM_SUCESSO);
		
		Assert.assertEquals(INDICATIVO_REMOCAO_REALIZADA, mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveRemoverSecretariaInexistente() {
		Long idSecretaria = 1L;
		Mockito.when(repository.existsById(idSecretaria)).thenReturn(false);
		
		MensagemDTO mensagemRetornada = service.removerSecretaria(idSecretaria);
		MensagemDTO mensagemEsperada = new MensagemDTO(SECRETARIA_INEXISTENTE);
		
		Assert.assertEquals(INDICATIVO_REMOCAO_NAO_REALIZADA, mensagemEsperada, mensagemRetornada);
	}
	
	public SecretariaDTO montarObjeto() {
		SecretariaDTO secretariaDTO = new SecretariaDTO();
		
		secretariaDTO.setArea(Area.SAUDE);
		secretariaDTO.setOrcamentoFolha(1000D);
		secretariaDTO.setOrcamentoProjeto(1000D);
		secretariaDTO.setTelefone("34999998888");
		secretariaDTO.setEndereco("Rua Borboletas Psicodélicas");
		secretariaDTO.setSite("zup.com.br");
		secretariaDTO.setEmail("zup@zup.com.br");
		return secretariaDTO;
	}
}
