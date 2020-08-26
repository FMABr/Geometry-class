package br.com.geometryclass.data.model.geometria;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Face {

	@Id
	private String nome;
	
	@OneToMany
	private List<Ponto3D> pontos;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Ponto3D> getPontos() {
		return pontos;
	}
	
	public void setPontos(List<Ponto3D> pontos) {
		this.pontos = pontos;
	}
	
	public Ponto3D getPonto(int index) {
		return this.pontos.get(index);
	}
	
	public void addPonto(Ponto3D ponto) {
		this.pontos.add(ponto);
	}
	
	public void addPonto(int index, Ponto3D ponto) {
		this.pontos.add(index, ponto);
	}
	
	public void deletePonto(Ponto3D ponto) {
		this.pontos.remove(ponto);
	}
	
	public void deletePonto(int index) {
		this.pontos.remove(index);
	}
}
