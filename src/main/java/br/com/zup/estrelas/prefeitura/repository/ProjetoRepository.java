package br.com.zup.estrelas.prefeitura.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.prefeitura.entity.Projeto;

@Repository
public interface ProjetoRepository extends CrudRepository<Projeto, Long> { 
	
}