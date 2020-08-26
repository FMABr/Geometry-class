package br.com.geometryclass;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.geometryclass.data.dao.FuncaoRepository;
import br.com.geometryclass.data.dao.PrivilegioRepository;
import br.com.geometryclass.data.model.usuario.Funcao;
import br.com.geometryclass.data.model.usuario.Privilegio;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private FuncaoRepository funcaoRepo;

	@Autowired
	private PrivilegioRepository privilegioRepo;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;

		Privilegio aulaRead = createPrivilegioIfNotFound("LER_AULA");
		Privilegio aulaCreate = createPrivilegioIfNotFound("CRIAR_AULA");
		Privilegio aulaUpdate = createPrivilegioIfNotFound("ATUALIZAR_AULA");
		Privilegio aulaDelete = createPrivilegioIfNotFound("DELETAR_AULA");
		Privilegio postRead = createPrivilegioIfNotFound("LER_POST");
		Privilegio postCreate = createPrivilegioIfNotFound("CRIAR_POST");

		createFuncaoIfNotFound("ADMIN", Arrays.asList(aulaCreate, aulaRead, aulaUpdate, aulaDelete, postRead, postCreate));
		createFuncaoIfNotFound("MODERADOR", Arrays.asList(aulaCreate, aulaRead, aulaUpdate, aulaDelete, postRead));
		createFuncaoIfNotFound("USUARIO", Arrays.asList(aulaRead, postRead));

		alreadySetup = true;
	}

	@Transactional
	Privilegio createPrivilegioIfNotFound(String nome) {
		Privilegio privilegio = privilegioRepo.findByNomeLike(nome);

		if (privilegio == null) {
			privilegio = new Privilegio(nome);
			privilegioRepo.save(privilegio);
		}

		return privilegio;
	}

	@Transactional
	Funcao createFuncaoIfNotFound(String nome, Collection<Privilegio> privilegios) {
		Funcao funcao = funcaoRepo.findByNomeLike(nome);
		
		if (funcao == null) {
			funcao = new Funcao(nome);
			funcao.setPrivilegios(privilegios);
			funcaoRepo.save(funcao);
		}

		return funcao;
	}
}