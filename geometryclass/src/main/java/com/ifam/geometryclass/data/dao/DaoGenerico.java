package com.ifam.geometryclass.data.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoGenerico<Entidade, Chave> extends CrudRepository<Entidade, Chave>{

	public Entidade findByNome(String nome);	
}
