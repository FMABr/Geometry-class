package com.ifam.geometryclass.data.model;

import java.util.List;

public class Geometria {

	private List<Ponto3D> pontos;
	private List<Aresta> arestas;
	private List<Face> faces;
	
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
	
	public List<Aresta> getArestas() {
		return arestas;
	}
	
	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}

	public Aresta getAresta(int index) {
		return this.arestas.get(index);
	}
	
	public void addAresta(Aresta Aresta) {
		this.arestas.add(Aresta);
	}
	
	public void addAresta(int index, Aresta Aresta) {
		this.arestas.add(index, Aresta);
	}
	
	public void deleteAresta(Aresta Aresta) {
		this.arestas.remove(Aresta);
	}
	
	public void deleteAresta(int index) {
		this.arestas.remove(index);
	}
	
	public List<Face> getFaces() {
		return faces;
	}
	
	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}
	
	public Face getFace(int index) {
		return this.faces.get(index);
	}
	
	public void addFace(Face Face) {
		this.faces.add(Face);
	}
	
	public void addFace(int index, Face Face) {
		this.faces.add(index, Face);
	}
	
	public void deleteFace(Face Face) {
		this.faces.remove(Face);
	}
	
	public void deleteFace(int index) {
		this.faces.remove(index);
	}
	
}
