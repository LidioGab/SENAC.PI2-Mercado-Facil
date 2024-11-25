package com.model;

public class Usuario {
    private Integer id;           // Representa o Id_Usuario
    private String nome;          // Representa o Nome_Usuario
    private String senha;         // Representa a Senha
    private String email;         // Representa o Email
    private String idGrupo;       // Representa o Id_Grupo (VARCHAR na tabela)
    private Integer idFuncionario; // Representa o Id_Funcionario

    // Construtor vazio
    public Usuario() {}

    // Construtor com parâmetros
    public Usuario(Integer id, String nome, String senha, String email, String idGrupo, Integer idFuncionario) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.idGrupo = idGrupo;
        this.idFuncionario = idFuncionario;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    // Método toString para facilitar a visualização dos dados do objeto
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", idGrupo='" + idGrupo + '\'' +
                ", idFuncionario=" + idFuncionario +
                '}';
    }
}
