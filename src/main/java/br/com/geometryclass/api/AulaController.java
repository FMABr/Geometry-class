package br.com.geometryclass.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.geometryclass.data.dto.AulaDto;
import br.com.geometryclass.data.dto.UsuarioDto;
import br.com.geometryclass.service.AulaService;

@Controller
public class AulaController {

	@Autowired
	private AulaService aulaServ;
	
	@GetMapping("/aula/{nome}")
	public String aula(Model model, @PathVariable String nome ) {
		AulaDto aula = aulaServ.aulaToDto(aulaServ.read(nome));
		aula.setNome(aula.getNome().replace("_", " "));
		model.addAttribute("aula", aula);
		return "aula";
	}
	
	@GetMapping({"/aulas", "/aulas/{pesquisa}"})
	public String aulas(Model model, @PathVariable(required = false) String pesquisa ) {
		model.addAttribute("usuario", new UsuarioDto());
		if(pesquisa != null) {
			model.addAttribute("aulas", pesquisar(pesquisa));
		} else {
			model.addAttribute("aulas", aulas());
		}
		return "aulas";
	}
	
	@GetMapping({"/editor", "/editor/{categoria}"})
	public String editor(Model model, @PathVariable(required = false) String categoria) {
		model.addAttribute("aula", new AulaDto());
		return "editor";
	}
	
	@PostMapping({"/editor", "/editor/{categoria}"})
	public String criarAula(@ModelAttribute("aula") @Valid AulaDto dto, Principal principal, @PathVariable(required = false) String categoria) {
		dto.setUsuario(principal.getName());
		dto.setNome(dto.getNome().trim());
		dto.setNome(dto.getNome().replace(" ", "_"));
		if(categoria != null) {
			dto.setTags(Arrays.asList("aula", categoria));
		} else {
			dto.setTags(Arrays.asList("aula"));
		}
		aulaServ.criar(dto);
		return "redirect:/";
	}
	
	public List<AulaDto> pesquisar(String pesquisa) {
		List<AulaDto> aulas = new ArrayList<>();
		aulaServ.readAllbyTag(pesquisa).stream().forEach(aula -> aulas.add(aulaServ.aulaToDto(aula)));
		return aulas;
	}
	
	public List<AulaDto> aulas() {
		List<AulaDto> aulas = new ArrayList<>();
		aulaServ.readAll().stream().forEach(aula -> aulas.add(aulaServ.aulaToDto(aula)));
		return aulas;
	}
}
