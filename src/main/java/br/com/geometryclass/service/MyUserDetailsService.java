package br.com.geometryclass.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.geometryclass.data.dao.UsuarioRepository;
import br.com.geometryclass.data.model.Usuario;
import br.com.geometryclass.data.model.usuario.Funcao;
import br.com.geometryclass.data.model.usuario.Privilegio;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//		System.out.println("Login: " + login);

		Usuario usuario = usuarioRepo.findByNome(login).orElseGet(() ->
						  usuarioRepo.findByEmail(login).orElseThrow(() -> 
						  new UsernameNotFoundException("Usuario não encontrado")));

//		System.out.println("Usuario: " + usuario);

		return new User(usuario.getNome(), usuario.getSenha(), true, true, true, true,
				getAuthorities(usuario.getFuncoes()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Funcao> funcoes) {
		return getGrantedAuthorities(getPrivileges(funcoes));
	}

	private List<String> getPrivileges(Collection<Funcao> funcoes) {
		List<String> privileges = new ArrayList<>();
		List<Privilegio> collection = new ArrayList<>();

		for (Funcao funcao : funcoes) {
			collection.addAll(funcao.getPrivilegios());
		}
		for (Privilegio privilegio : collection) {
			privileges.add(privilegio.getNome());
		}

		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}

		return authorities;
	}

}
