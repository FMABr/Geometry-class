package br.com.geometryclass.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geometryclass.data.model.usuario.Privilegio;

@Repository
public interface PrivilegioRepository extends JpaRepository<Privilegio, Long> {

	public Privilegio findByNomeLike(String nome);
}
