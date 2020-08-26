package br.com.geometryclass.api;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.geometryclass.data.dto.PostDto;
import br.com.geometryclass.data.dto.UsuarioDto;
import br.com.geometryclass.service.PostService;
import br.com.geometryclass.service.UsuarioService;


@Controller
public class HomeController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping({"/", "/home"})
	public String homepage(Model model) {
		model.addAttribute("usuario", new UsuarioDto());
		model.addAttribute("post", new PostDto());
		model.addAttribute("postList", posts());
		return "index";
	}
	
	@PostMapping("/cadastro")
	public String cadastro(Model model, @Valid @ModelAttribute("usuario") UsuarioDto dto, BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("postList", posts());
			return "index";
		} else {
			usuarioService.register(dto);
			return "redirect:/";
		}
	}
	
	@PostMapping("/post")
	public String post(Principal principal, @Valid @ModelAttribute("post") PostDto dto, BindingResult result) {
		dto.setUsuario(principal.getName());
		postService.register(dto);
		return "redirect:/";
	}
	
	private List<PostDto> posts() {
		List<PostDto> posts = new ArrayList<>();
		postService.readAll().stream().forEach(post -> posts.add(postService.EntityToDto(post)));
		return posts;
	}
}
	