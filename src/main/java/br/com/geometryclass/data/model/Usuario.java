package br.com.geometryclass.data.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import br.com.geometryclass.data.model.usuario.Funcao;

@Entity
public class Usuario {

	@Id
	@Column(length = 16)
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(unique = true, nullable = false, length = 32)
	@Size(min = 4, max = 32)
	@NotBlank
	private String nome;

	@Column(unique = true, nullable = false)
	@Email
	@NotBlank
	private String email;

	@Column(nullable = false)
	@Size(min = 6)
	@NotBlank
	private String senha;

	@ManyToMany
	@JoinTable(name = "usuarios_funcoes", 
	joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "funcao_id", referencedColumnName = "id"))
	private Collection<Funcao> funcoes;

	@OneToMany(mappedBy = "usuario")
	@Size(max = 20)
	private List<Geometria> geometrias;
	
	@OneToMany(mappedBy = "usuario")
	@Size(max = 20)
	private List<Aula> aulas;

	@OneToMany(mappedBy = "usuario")
	@Size(max = 20)
	private List<Post> posts;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Collection<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Collection<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<Geometria> getGeometrias() {
		return geometrias;
	}

	public void setGeometrias(List<Geometria> geometrias) {
		this.geometrias = geometrias;
	}
	
	public List<Aula> getAulas() {
		return aulas;
	}
	
	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	@Override
	public String toString() {
		return nome + " - " + email + " - " + senha;
	}
}