package com.ifam.geometryclass.data.model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

public class GeometriaAula extends Geometria {

	@Embeddable
	class GeometriaKey{
		protected String aulaNome;
		protected String nome;
		
		public String getAulaNome() {
			return aulaNome;
		}
		
		public void setAulaNome(String aulaNome) {
			this.aulaNome = aulaNome;
		}
		
		public String getNome() {
			return nome;
		}
		
		public void setNome(String nome) {
			this.nome = nome;
		}
	}
	
	@EmbeddedId
	private GeometriaKey chave;
}
