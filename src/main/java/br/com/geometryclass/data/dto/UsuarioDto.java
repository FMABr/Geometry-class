package br.com.geometryclass.data.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDto {

	@Size(min = 4, max = 32, message = "De 4 à 32 caracteres")
	@NotBlank(message = "Informe um nome de usuário")
	private String nome;

	@NotBlank(message = "Informe um email")
	@Email(message = "Use um email válido")
	private String email;

	@Size(min = 4, max = 128, message = "De 4 à 128 caracteres")
	@NotBlank(message = "Informe uma senha")
	private String senha;
	
	@NotBlank(message = "confirme a senha")
	private String confirmarSenha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

}
