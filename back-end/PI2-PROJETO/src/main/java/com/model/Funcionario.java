package com.model;

public class Funcionario {
	private int id;
	private String nome;
	private String sobrenome;
	private String email;
	private String cpf; 
	private String situacao;
	private String cargo;
	
	public Funcionario(int id, String nome,String sobrenome, String email, String cpf, String situacao, String cargo) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.cpf = cpf;
		this.situacao = situacao;
		this.cargo = cargo;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}


	public void setSobrenome(String sobrenome) {
        String[] partes = getNome().split(" ");
        String sb = "";
        
        for(int i = 1; i < partes.length; i++) {
        	sb = sb.concat(partes[i] + " ");
        }
        
        this.sobrenome = sb;
        
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


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
	
	
	
}
