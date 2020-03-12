package com.ifam.geometryclass.data.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class GeometriaAula extends Geometria {
	
	@Id
	@OneToOne
	@JoinColumn(name = "nome")
	private Aula aula;
}
