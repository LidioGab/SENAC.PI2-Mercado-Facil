package com.gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.model.Funcionario;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connetion) {
        this.connection = connection;
    }

    public void CadastrarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO usuario_interno (Nome_Usuario, Senha, Email, Data_Criacao, Ultimo_Acesso, Id_Grupo, Id_Funcionario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            //stmt.setInt(3, funcionario.getIdGrupo());
            //stmt.setInt(4, funcionario.getIdFuncionario());
            stmt.executeUpdate();
        }

    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionario SET nome = ?, sobrenome = ?, email = ?, cpf = ?, situacao = ?, cargo = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getCpf());
            stmt.setString(5, funcionario.getSituacao());
            stmt.setString(6, funcionario.getCargo());
            stmt.setInt(7, funcionario.getId());
            stmt.executeUpdate();
        }

    }

}