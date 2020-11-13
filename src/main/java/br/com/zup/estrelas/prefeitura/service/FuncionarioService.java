package br.com.zup.estrelas.prefeitura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

	private static final String SALARIO_FUNCIONARIO_INVALIDO = "Não foi possivel cadastrar funcionario, salario do funcionario invalido.";

	private static final String FUNCIONARIO_EXCLUIDO_COM_SUCESSO = "Funcionario excluido com sucesso!";

	private static final String SECRETARIA_INVALIDA = "Não foi possivel alterar funcionario, nova secretaria invalida.";

	private static final String FUNCIONARIO_ALTERADO_COM_SUCESSO = "Funcionario alterado com sucesso!";

	private static final String NOVO_SALARIO_MENOR_SALARIO_ATUAL = "Infelizmente não foi possivel completar a operação, novo salario não pode ser menor que salario atual.";

	private static final String ALTERAR_ORCAMENTO_FOLHA_INSUFICIENTE = "Não foi possivel alterar funcionario, nova secretaria não possui orçamento em folha suficiente para contratação.";

	private static final String FUNCIONARIO_CPF_EXISTENTE = "Infelizmente não foi possivel completar a operação, já possui um funcionario com este CPF.";

	private static final String FUNCIONARIO_INEXISTENTE = "Funcionario inexistente.";

	private static final String SECRETARIA_INEXISTENTE = "Não foi possivel cadastrar funcionario, secretaria inexistente.";

	private static final String CPF_CADASTRADO = "Não foi possivel cadastrar funcionario, cpf já cadastrado.";

	private static final String ADICIONAR_ORCAMENTO_FOLHA_INSUFICIENTE = "Não foi possivel cadastrar funcionario, "
			+ "secretaria não possui orçamento em folha suficiente para contratação.";

	private static final String FUNCIONARIO_CADASTRADO_COM_SUCESSO = "Funcionario cadastrado com sucesso!";

	@Autowired
	FuncionarioRepository repository;

	@Autowired
	SecretariaRepository secretariaRepository;

	public MensagemDTO adicionarFuncionario(FuncionarioDTO funcionarioDTO) {
		Optional<Secretaria> secretariaOptional = secretariaRepository.findById(funcionarioDTO.getIdSecretaria());

		if (secretariaOptional.isEmpty()) {
			return new MensagemDTO(SECRETARIA_INEXISTENTE);
		}

		Secretaria secretaria = secretariaOptional.get();

		boolean verificaSalarioIgualInferiorZero = funcionarioDTO.getSalario() <= 0;
		boolean verificaCpfCadastrado = repository.findByCpf(funcionarioDTO.getCpf()).isPresent();
		boolean salarioCompativelOrcamentoFolhaSecretaria = funcionarioDTO.getSalario() <= secretaria
				.getOrcamentoFolha();

		if (verificaSalarioIgualInferiorZero) {
			return new MensagemDTO(SALARIO_FUNCIONARIO_INVALIDO);
		}

		if (verificaCpfCadastrado) {
			return new MensagemDTO(CPF_CADASTRADO);
		}

		if (salarioCompativelOrcamentoFolhaSecretaria) {
			subtrairOrcamentoFolhaSecretaria(secretaria, funcionarioDTO.getSalario());
			Funcionario funcionario = new Funcionario();

			BeanUtils.copyProperties(funcionarioDTO, funcionario);

			funcionario.setSecretaria(secretaria);
			funcionario.setDataAdmissao(LocalDate.now());
			repository.save(funcionario);

			return new MensagemDTO(FUNCIONARIO_CADASTRADO_COM_SUCESSO);
		}

		return new MensagemDTO(ADICIONAR_ORCAMENTO_FOLHA_INSUFICIENTE);
	}

	public Funcionario buscarFuncionarioPorId(Long idFuncionario) {
		return repository.findById(idFuncionario).orElse(null);
	}

	public List<Funcionario> listarFuncionarios() {
		return (List<Funcionario>) repository.findAll();
	}

	public MensagemDTO alterarFuncionario(Long idFuncionario, FuncionarioDTO funcionarioDTO) {
		Optional<Funcionario> funcionarioOptional = repository.findById(idFuncionario);

		if (funcionarioOptional.isEmpty()) {
			return new MensagemDTO(FUNCIONARIO_INEXISTENTE);
		}

		Funcionario funcionario = repository.findByCpf(funcionarioDTO.getCpf()).get();

		boolean verificaSalarioIgualInferiorZero = funcionarioDTO.getSalario() <= 0;
		boolean verificaCpfCadastrado = repository.findByCpf(funcionarioDTO.getCpf()).isPresent();
		boolean verificaIdConsultadoDiferenteIdEnviado = funcionario.getIdFuncionario() != idFuncionario;

		if (verificaSalarioIgualInferiorZero) {
			return new MensagemDTO(SALARIO_FUNCIONARIO_INVALIDO);
		}

		if (verificaCpfCadastrado && verificaIdConsultadoDiferenteIdEnviado) {
			return new MensagemDTO(FUNCIONARIO_CPF_EXISTENTE);
		}

		boolean verificaSalarioDTOMenorSalarioAtual = funcionarioDTO.getSalario() < funcionario.getSalario();

		if (verificaSalarioDTOMenorSalarioAtual) {
			return new MensagemDTO(NOVO_SALARIO_MENOR_SALARIO_ATUAL);
		}

		Optional<Secretaria> novaSecretariaOptional = secretariaRepository.findById(funcionarioDTO.getIdSecretaria());

		if (novaSecretariaOptional.isEmpty()) {
			return new MensagemDTO(SECRETARIA_INVALIDA);
		}

		Secretaria secretaria = secretariaRepository.findById(funcionarioDTO.getIdSecretaria()).get();

		boolean verificaMudancaSecretaria = funcionarioDTO.getIdSecretaria() != funcionario.getSecretaria()
				.getIdSecretaria();

		if (verificaMudancaSecretaria) {
			if (!alterarOrcamentoFolhaSecretarias(funcionarioDTO, funcionario)) {
				return new MensagemDTO(ALTERAR_ORCAMENTO_FOLHA_INSUFICIENTE);
			}
		}

		boolean verificaSalarioAtualIgualSalarioDTO = funcionario.getSalario().equals(funcionarioDTO.getSalario());
		boolean salarioCompativelOrcamentoFolha = funcionarioDTO.getSalario() <= secretaria.getOrcamentoFolha();
		boolean validaNecessidadeAlteracaoOrcamentoFolhaSecretaria = !verificaSalarioAtualIgualSalarioDTO
				&& !verificaMudancaSecretaria && salarioCompativelOrcamentoFolha;

		if (validaNecessidadeAlteracaoOrcamentoFolhaSecretaria) {
			Double diferencaSalarioAtualComNovoSalario = funcionarioDTO.getSalario() - funcionario.getSalario();
			subtrairOrcamentoFolhaSecretaria(secretaria, diferencaSalarioAtualComNovoSalario);
		}

		BeanUtils.copyProperties(funcionarioDTO, funcionario);
		funcionario.setIdFuncionario(idFuncionario);
		funcionario.setSecretaria(secretaria);

		repository.save(funcionario);

		return new MensagemDTO(FUNCIONARIO_ALTERADO_COM_SUCESSO);
	}

	public MensagemDTO removerFuncionario(Long idFuncionario) {
		Optional<Funcionario> funcionarioOptional = repository.findById(idFuncionario);
		
		if (funcionarioOptional.isEmpty()) {
			return new MensagemDTO(FUNCIONARIO_INEXISTENTE);
		}
		
		Funcionario funcionario = funcionarioOptional.get();
		Secretaria secretaria = secretariaRepository.findById(funcionario.getSecretaria().getIdSecretaria()).get();
		adicionarOrcamentoFolhaSecretaria(secretaria, funcionario.getSalario());

		repository.delete(funcionario);

		return new MensagemDTO(FUNCIONARIO_EXCLUIDO_COM_SUCESSO);
	}

	public boolean alterarOrcamentoFolhaSecretarias(FuncionarioDTO funcionarioDTO, Funcionario funcionario) {
		Secretaria novaSecretaria = secretariaRepository.findById(funcionarioDTO.getIdSecretaria()).get();
		Secretaria secretariaAtual = secretariaRepository.findById(funcionario.getSecretaria().getIdSecretaria()).get();

		boolean salarioCompativelOrcamentoFolhaNovaSecretaria = funcionarioDTO.getSalario() <= novaSecretaria
				.getOrcamentoFolha();

		if (salarioCompativelOrcamentoFolhaNovaSecretaria) {
			Double salarioFuncionario = funcionarioDTO.getSalario();
			Double salarioSecretariaAtual = funcionario.getSalario();

			subtrairOrcamentoFolhaSecretaria(novaSecretaria, salarioFuncionario);
			adicionarOrcamentoFolhaSecretaria(secretariaAtual, salarioSecretariaAtual);
			return true;
		}
		return false;
	}

	public void subtrairOrcamentoFolhaSecretaria(Secretaria secretaria, Double salarioFuncionario) {
		secretaria.setOrcamentoFolha(secretaria.getOrcamentoFolha() - salarioFuncionario);
		secretariaRepository.save(secretaria);
	}

	public void adicionarOrcamentoFolhaSecretaria(Secretaria secretaria, Double salarioFuncionario) {
		secretaria.setOrcamentoFolha(secretaria.getOrcamentoFolha() + salarioFuncionario);
		secretariaRepository.save(secretaria);
	}

}
