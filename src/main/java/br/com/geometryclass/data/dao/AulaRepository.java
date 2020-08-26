package br.com.geometryclass.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.geometryclass.data.model.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {

	@Query("SELECT a FROM Aula a JOIN a.tags t WHERE t = LOWER(:teste)")
	public List<Aula> findByTag(@Param("teste") String tag);
	
	public List<Aula> findByNomeContaining(String nome);

	public Optional<Aula> findByNome(String nome);

}
