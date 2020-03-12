package com.ifam.geometryclass.data.model;

import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GeometriaUsuario extends Geometria {
	
	@Embeddable
	class GeometriaKey{
		protected UUID usuarioId;
		protected String nome;
		
		public UUID getUsuarioId() {
			return usuarioId;
		}
		
		public void setUsuarioId(UUID usuarioId) {
			this.usuarioId = usuarioId;
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
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Usuario usuario;
}
