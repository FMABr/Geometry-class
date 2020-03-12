package com.ifam.geometryclass.data.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class DaoGenerico<T, I extends Serializable>{

	@Autowired
	protected EntityManager entityManager;
	
	private Class<T> persistido;
	
	public DaoGenerico(Class<T> persistido) {
		this.persistido = persistido;
	}
}
