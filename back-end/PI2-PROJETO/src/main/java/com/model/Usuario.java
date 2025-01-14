package com.model;

public class Usuario {

	private String nome; 
	private String senha; 
	private String email; 
	private String idGrupo; 
	private Integer idUsuario; 
	private Integer idFuncionario;

	public Usuario(String nome, String senha, String email, String idGrupo, Integer idUsuario, Integer idFuncionario) {
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.idGrupo = idGrupo;
		this.idUsuario = idUsuario;
		this.idFuncionario = idFuncionario;

	}

	public Usuario(String nome, String senha, String email, String idGrupo, Integer idUsuario) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.idGrupo = idGrupo;
		this.idUsuario = idUsuario;
	}
	
	public Usuario() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

}
