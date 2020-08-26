package br.com.geometryclass.data.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class AulaDto {
	
	@NotBlank
	private String nome;

	@NotBlank
	private String conteudo;
	
	private String usuario;
	
	private List<String> tags;
	
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
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
