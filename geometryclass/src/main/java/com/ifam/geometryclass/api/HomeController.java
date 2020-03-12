package com.ifam.geometryclass.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifam.geometryclass.data.model.Usuario;
import com.ifam.geometryclass.service.UsuarioService;

@RestController
@RequestMapping
public class HomeController {
	
	private UsuarioService userService;
	
	@PostMapping()
	public void postUsuario(@RequestBody Usuario usuario) {
		userService.createUsuario(usuario);
	}
	
	@GetMapping(value = "/{id}")
	public Usuario getUsuarios(@PathVariable UUID id) {
		return userService.readUsuarioById(id);
	}
}
