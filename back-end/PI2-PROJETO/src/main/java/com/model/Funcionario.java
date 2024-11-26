package com.model;

public class Funcionario {
	private int id;
	private String nome;
	private String email;
	private String cpf;
	private String situacao;
	private int cargo;
	private int setor;

	public Funcionario(int id, String nome, String email, String cpf, String situacao, int cargo,
			int setor) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.situacao = situacao;
		this.cargo = cargo;
		this.setor = setor;
	}
	public Funcionario() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public int getSetor() {
		return setor;
	}

	public void setSetor(int setor) {
		this.setor = setor;
	}

}