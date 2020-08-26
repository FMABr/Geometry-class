package br.com.geometryclass.data.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geometryclass.data.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	public Optional<Usuario> findByNome(String nome);

	public Optional<Usuario> findByEmail(String email);

	public List<Usuario> findByNomeContaining(String nome);

}
