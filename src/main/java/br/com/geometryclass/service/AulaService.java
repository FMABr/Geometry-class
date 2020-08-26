package br.com.geometryclass.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.geometryclass.data.dao.AulaRepository;
import br.com.geometryclass.data.dao.UsuarioRepository;
import br.com.geometryclass.data.dto.AulaDto;
import br.com.geometryclass.data.model.Aula;

@Service
public class AulaService {

	@Autowired
	private AulaRepository aulaRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	public boolean criar(AulaDto dto) {
		aulaRepo.save(dtoToAula(dto));		
		return true;
	}
	
	public Aula dtoToAula(AulaDto dto) {
		Aula aula = new Aula();
		aula.setNome(dto.getNome());
		aula.setConteudo(dto.getConteudo());
		aula.setUsuario(usuarioRepo.findByNome(dto.getUsuario()).get());
		aula.setTags(dto.getTags());
		return aula;
	}
	
	public AulaDto aulaToDto(Aula aula) {
		AulaDto dto = new AulaDto();
		System.out.println(aula);
		dto.setNome(aula.getNome());
		dto.setConteudo(aula.getConteudo());
		dto.setUsuario(aula.getUsuario().getNome());
		dto.setTags(aula.getTags());
		return dto;
	}
	
	public Aula read(Integer id) {
		Optional<Aula> leitura = Optional.empty();

		try {
			leitura = aulaRepo.findById(id);
			return leitura.orElse(null);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public Aula read(String nome) {
		Optional<Aula> leitura = Optional.empty();

		try {
			leitura = aulaRepo.findByNome(nome);
			return leitura.orElse(null);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Aula> readAll() {
		List<Aula> leitura = new ArrayList<>();

		try {
			aulaRepo.findAll().forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Aula> readAll(String nome) {
		List<Aula> leitura = new ArrayList<>();

		try {
			aulaRepo.findByNomeContaining(nome).forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Aula> readAllbyTag(String tag) {
		List<Aula> leitura = new ArrayList<>();
		try {
			aulaRepo.findByTag(tag).forEach(leitura::add);
			System.out.println(leitura.size());
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
