package br.com.geometryclass.data.model.usuario;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.com.geometryclass.data.model.Usuario;

@Entity
public class Funcao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50)
	private String nome;

	@ManyToMany(mappedBy = "funcoes")
	private Collection<Usuario> usuarios;

	@ManyToMany
	@JoinTable(name = "funcoes_privilegios", joinColumns = @JoinColumn(name = "funcao_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilegio_id", referencedColumnName = "id"))
	private Collection<Privilegio> privilegios;

	public Funcao() {}
	
	public Funcao(String nome) {
		this.nome = nome;
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public Collection<Privilegio> getPrivilegios() {
		return privilegios;
	}
	
	public void setPrivilegios(Collection<Privilegio> privilegios) {
		this.privilegios = privilegios;
	}
}
