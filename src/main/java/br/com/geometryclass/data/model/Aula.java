package br.com.geometryclass.data.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Aula {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(unique = true, nullable = false, length = 120)
	@Size(max = 120)
	@NotBlank
	private String nome;

	@Column(nullable = false, columnDefinition = "text")
	@NotBlank
	private String conteudo;
	
	@ManyToOne
	@JoinColumn(name = "geometria_id", referencedColumnName = "id")
	private Geometria geometria;

	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	@ElementCollection
	private List<String> tags;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public Geometria getGeometria() {
		return geometria;
	}
	
	public void setGeometria(Geometria geometria) {
		this.geometria = geometria;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getTag(int index) {
		return tags.get(index);
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public void addTag(int index, String tag) {
		tags.add(index, tag);
	}
	
	public void deleteTag(String tag) {
		tags.remove(tag);
	}
	
	public void deleteTag(int index) {
		tags.remove(index);
	}
}
