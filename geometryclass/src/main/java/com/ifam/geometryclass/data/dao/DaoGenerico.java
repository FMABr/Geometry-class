package com.ifam.geometryclass.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoGenerico<Entidade, Chave> extends CrudRepository<Entidade, Chave>{

	public Optional<Entidade> findByNomeLike(String nome);
	public List<Entidade> findByNomeContaining(String nome);
}
