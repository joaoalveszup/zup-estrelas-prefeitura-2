package br.com.zup.estrelas.prefeitura.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.prefeitura.dto.FuncionarioDTO;
import br.com.zup.estrelas.prefeitura.dto.MensagemDTO;
import br.com.zup.estrelas.prefeitura.entity.Funcionario;
import br.com.zup.estrelas.prefeitura.entity.Secretaria;
import br.com.zup.estrelas.prefeitura.repository.FuncionarioRepository;
import br.com.zup.estrelas.prefeitura.repository.SecretariaRepository;

@Service
public class FuncionarioService implements IFuncionarioService {

	private static final String CPF_CADASTRADO = "Não foi possivel cadastrar funcionario, cpf já cadastrado.";

	private static final String ORCAMENTO_FOLHA_INSUFICIENTE = "Não foi possivel cadastrar funcionario, "
			+ "secretaria não possui orçamento em folha suficiente para contratação.";

	private static final String FUNCIONARIO_CADASTRADO_COM_SUCESSO = "Funcionario cadastrado com sucesso!";

	@Autowired
	FuncionarioRepository repository;

	@Autowired
	SecretariaRepository secretariaRepository;

	public MensagemDTO adicionarFuncionario(FuncionarioDTO funcionarioDTO) {	
		Secretaria secretaria = secretariaRepository.findById(funcionarioDTO.getIdSecretaria()).get();

		boolean salarioCompativelOrcamentoFolhaSecretaria = funcionarioDTO.getSalario() <= secretaria
				.getOrcamentoFolha();
		boolean verificaCpfCadastrado = repository.findByCpf(funcionarioDTO.getCpf()).isPresent();

		if (verificaCpfCadastrado) {
			return new MensagemDTO(CPF_CADASTRADO);
		}

		if (salarioCompativelOrcamentoFolhaSecretaria) {
			alterarOrcamentoFolhaSecretaria(secretaria, funcionarioDTO.getSalario());
			Funcionario funcionario = new Funcionario();

			BeanUtils.copyProperties(funcionarioDTO, funcionario);

			funcionario.setSecretaria(secretaria);
			funcionario.setDataAdmissao(LocalDate.now());
			repository.save(funcionario);

			return new MensagemDTO(FUNCIONARIO_CADASTRADO_COM_SUCESSO);
		}

		return new MensagemDTO(ORCAMENTO_FOLHA_INSUFICIENTE);
	}

	public Funcionario buscarFuncionarioPorId(Long idFuncionario) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Funcionario> listarFuncionarios() {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO alterarFuncionario(Long idFuncionario, FuncionarioDTO funcionarioDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public MensagemDTO removerFuncionario(Long idFuncionario) {
		// TODO Auto-generated method stub
		return null;
	}

	public void alterarOrcamentoFolhaSecretaria(Secretaria secretaria, Double salarioFuncionario) {
		secretaria.setOrcamentoFolha(secretaria.getOrcamentoFolha() - salarioFuncionario);
		secretariaRepository.save(secretaria);
	}
}
