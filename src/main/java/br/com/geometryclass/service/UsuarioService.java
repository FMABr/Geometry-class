package br.com.geometryclass.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.geometryclass.data.dao.FuncaoRepository;
import br.com.geometryclass.data.dao.UsuarioRepository;
import br.com.geometryclass.data.dto.UsuarioDto;
import br.com.geometryclass.data.model.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private FuncaoRepository funcaoRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	public boolean register(UsuarioDto dto) {
		if (isEmailRegistered(dto.getEmail())) {
			return false;
		}
		
		Usuario usuario = dtoToEntity(dto);

		return createOrUpdate(usuario);
	}
	
	public Usuario dtoToEntity(UsuarioDto dto) {
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(encoder.encode(dto.getSenha()));
		usuario.setFuncoes(Arrays.asList(funcaoRepo.findByNomeLike("USUARIO")));
		return usuario;
	}

	public boolean createOrUpdate(Usuario usuario) {
		try {
			usuarioRepo.save(usuario);
			return true;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Usuario read(UUID id) {
		Optional<Usuario> leitura = Optional.empty();

		try {
			leitura = usuarioRepo.findById(id);
			return leitura.orElse(null);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Usuario read(String nome) {
		Optional<Usuario> leitura = Optional.empty();

		try {
			leitura = usuarioRepo.findByNome(nome);
			return leitura.orElse(null);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Usuario> readAll() {
		List<Usuario> leitura = new ArrayList<>();

		try {
			usuarioRepo.findAll().forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Usuario> readAll(String nome) {
		List<Usuario> leitura = new ArrayList<>();

		try {
			usuarioRepo.findByNomeContaining(nome).forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean delete(Usuario usuario) {
		try {
			usuarioRepo.delete(usuario);
			return true;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}
	};

	public boolean delete(UUID id) {
		try {
			usuarioRepo.deleteById(id);
			return true;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private boolean isEmailRegistered(String email) {
		return usuarioRepo.findByEmail(email).isPresent();
	}
}
