package com.ifam.geometryclass.data.model;

public class Ponto3D {
	
	private String nome;
	private double[] coordenadas = new double[3];
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double[] getCoordenadas() {
		return coordenadas;
	}
	
	public void setCoordenadas(double[] coordenadas) {
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
