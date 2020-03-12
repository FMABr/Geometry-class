package com.ifam.geometryclass.service;

import com.ifam.geometryclass.data.dao.DaoGenerico;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
  private DaoGenerico<Usuario, UUID> dao; 

  public int createUsuario(Usuario usuario){
    try{
      dao.save(usuario);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }

  public Optional<Usuario> readUsuarioById(UUID id){
    try{
      dao.findById(id);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }

  public Optional<Usuario> readUsuarioByNome(String nome){
    try{
      dao.findByNome(nome);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }

  public Usuario readAllUsuarios(){
    try{
      dao.findAll();
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }

  public int updateUsuario(Usuario usuario){
    try{
      dao.save(usuario);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }

  public int deleteUsuario(Usuario usuario){
    try{
      dao.delete(usuario);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  };

  public int deleteUsuarioById(UUID id){
    try{
      dao.deleteById(id);
    } catch (IllegalArgumentException ex){
      ex.printStackTrace();
    }
  }
}
