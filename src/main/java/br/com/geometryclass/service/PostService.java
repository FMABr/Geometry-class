package br.com.geometryclass.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.geometryclass.data.dao.PostRepository;
import br.com.geometryclass.data.dao.UsuarioRepository;
import br.com.geometryclass.data.dto.PostDto;
import br.com.geometryclass.data.model.Post;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UsuarioRepository userRepo;
	
	public boolean register(PostDto dto) {		
		Post post = dtoToEntity(dto);

		return createOrUpdate(post);
	}
	
	public List<Post> readAll() {
		List<Post> leitura = new ArrayList<>();

		try {
			postRepo.findAll().forEach(leitura::add);
			return leitura;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean createOrUpdate(Post post) {
		try {
			postRepo.save(post);
			return true;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public Post dtoToEntity (PostDto dto) {
		Post post = new Post();
		post.setTitulo(dto.getTitulo());
		post.setConteudo(dto.getConteudo());
		post.setData(dto.getData());
		post.setUsuario(userRepo.findByNome(dto.getUsuario()).get());
		return post;
	}
	
	public PostDto EntityToDto(Post post) {
		PostDto dto = new PostDto();
		dto.setId(post.getId());
		dto.setTitulo(post.getTitulo());
		dto.setConteudo(post.getConteudo());
		dto.setData(post.getData());
		dto.setUsuario(post.getUsuario().getNome());
		return dto;
	}
}
