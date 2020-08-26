package br.com.geometryclass.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geometryclass.data.model.usuario.Funcao;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

	public Funcao findByNomeLike(String nome);
}
