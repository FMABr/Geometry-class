package br.com.geometryclass.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.geometryclass.data.model.geometria.Aresta;
import br.com.geometryclass.data.model.geometria.Face;
import br.com.geometryclass.data.model.geometria.Ponto3D;

@Entity
public class Geometria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@NotBlank
	private String nome;

	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ponto3D> pontos;
	
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Aresta> arestas;
	
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Face> faces;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	public Geometria() {}
	
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
