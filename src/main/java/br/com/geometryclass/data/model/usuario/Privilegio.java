package br.com.geometryclass.data.model.usuario;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Privilegio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50)
	private String nome;

	@ManyToMany(mappedBy = "privilegios")
	private Collection<Funcao> funcoes;

	public Privilegio() {}
	
	public Privilegio(String nome) {
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

	public Collection<Funcao> getFuncoes() {
		return funcoes;
	}
	
	public void setFuncoes(Collection<Funcao> funcoes) {
		this.funcoes = funcoes;
	}
}
