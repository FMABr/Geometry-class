package br.com.geometryclass.data.model.geometria;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ponto3D {
	
	@Id
	private String nome;
	
	private Double[] coordenadas = new Double[3];
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double[] getCoordenadas() {
		return coordenadas;
	}
	
	public void setCoordenadas(Double[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public double getX() {
		return coordenadas[0];
	}
	
	public void setX(double x) {
		coordenadas[0] = x;
	}

	public double getY() {
		return coordenadas[1];
	}
	
	public void setY(double y) {
		coordenadas[1] = y;
	}

	public double getZ() {
		return coordenadas[2];
	}
	
	public void setZ(double z) {
		coordenadas[2] = z;
	}
	
}
