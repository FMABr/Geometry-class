package com.ifam.geometryclass.data.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Aula {

	@Id
	private String nome;
	private String conteudo;
	private List<String> tags;
	private GeometriaAula geometria;
	
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
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getTag(int index) {
		return this.tags.get(index);
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
	
	public Geometria getGeometria() {
		return geometria;
	}
	
	public void setGeometria(GeometriaAula geometria) {
		this.geometria = geometria;
	}
}
