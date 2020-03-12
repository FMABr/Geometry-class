package com.ifam.geometryclass.data.model;

public class Aresta {
	
	private String nome;
	private Ponto3D[] pontos = new Ponto3D[2];
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Ponto3D getComeco() {
		return pontos[0];
	}
	
	public void setComeco(Ponto3D comeco) {
		pontos[0] = comeco;
	}
	
	public Ponto3D getFim() {
		return pontos[1];
	}
	
	public void setFim(Ponto3D fim) {
		pontos[1] = fim;
	}
}
