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
		String sql = "INSERT INTO Funcionarios (Nome, Sobrenome, CPF, Cargo, Setor, Situacao, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, funcionario.getNome());
			
			stmt.setString(2, funcionario.getSobrenome());
			
			stmt.setString(3, funcionario.getCpf());
			
			stmt.setString(4, funcionario.getCargo());
			
			stmt.setString(5, funcionario.getSetor());
			
			stmt.setString(6, funcionario.getSituacao());
			
			stmt.setString(7, funcionario.getEmail());
			
			stmt.executeUpdate();
		}

	}

	public void atualizar(Funcionario funcionario) throws SQLException {
		String sql = "UPDATE Funcionarios SET Nome = ?, Sobrenome = ?, CPF = ?, Cargo = ?, Setor = ?, Situacao = ?, Email = ? WHERE cpf = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, funcionario.getNome());
			
			stmt.setString(2, funcionario.getSobrenome());
			
			stmt.setString(3, funcionario.getCpf());
			
			stmt.setString(4, funcionario.getCargo());
			
			stmt.setString(5, funcionario.getSetor());
			
			stmt.setString(6, funcionario.getSituacao());
			
			stmt.setString(7, funcionario.getEmail());
			
			stmt.setString(8, funcionario.getCpf());
			
			stmt.executeUpdate();
		}

	}

}