package br.com.zup.estrelas.prefeitura.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.prefeitura.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
	Optional<Funcionario> findByCpf(String cpf);

}