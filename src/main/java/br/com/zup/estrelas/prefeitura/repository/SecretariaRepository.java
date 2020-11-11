package br.com.zup.estrelas.prefeitura.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.prefeitura.entity.Secretaria;

@Repository
public interface SecretariaRepository extends CrudRepository<Secretaria, Long> { 
	
}