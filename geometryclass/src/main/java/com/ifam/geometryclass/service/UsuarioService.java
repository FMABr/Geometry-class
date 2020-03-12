package com.ifam.geometryclass.service;

import com.ifam.geometryclass.data.dao.DaoGenerico;
import com.ifam.geometryclass.data.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	private DaoGenerico<Usuario, UUID> dao;

	public int createUsuario(Usuario usuario) {
		try {
			dao.save(usuario);
			return 0;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	public Usuario readUsuarioById(UUID id) {
		Optional<Usuario> leitura = Optional.empty();

		try {
			leitura = dao.findById(id);
			return leitura.isPresent() ? leitura.get() : null;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Usuario readUsuarioByNome(String nome) {
		Optional<Usuario> leitura = Optional.empty();

		try {
			leitura = dao.findByNomeLike(nome);
			return leitura.isPresent() ? leitura.get() : null;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Usuario> readAllUsuarios() {
		List<Usuario> leitura = new ArrayList<>();

		try {
			dao.findAll().forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Usuario> readAllUsuarios(String nome) {
		List<Usuario> leitura = new ArrayList<>();

		try {
			dao.findByNomeContaining(nome).forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public int updateUsuario(Usuario usuario) {
		try {
			dao.save(usuario);
			return 0;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	public int deleteUsuario(Usuario usuario) {
		try {
			dao.delete(usuario);
			return 0;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return -1;
		}
	};

	public int deleteUsuarioById(UUID id) {
		try {
			dao.deleteById(id);
			return 0;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
}
