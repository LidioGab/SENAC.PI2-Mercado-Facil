package com.gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.model.Funcionario;

public class FuncionarioDAO {
	private Connection connection;

	public FuncionarioDAO(Connection connection) {
		this.connection = connection;
	}

	public void CadastrarFuncionario(Funcionario funcionario) throws SQLException {
		String sql = "INSERT INTO Funcionarios (Nome, CPF, Cargo, Setor, Situacao, Email) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, funcionario.getNome());
			
			stmt.setString(2, funcionario.getCpf());
			
			stmt.setInt(3, funcionario.getCargo());
			
			stmt.setInt(4, funcionario.getSetor());
			
			stmt.setString(5, funcionario.getSituacao());
			
			stmt.setString(6, funcionario.getEmail());
			System.out.println(stmt);
			stmt.executeUpdate();
		}

	}

	public void atualizar(Funcionario funcionario) throws SQLException {
		String sql = "UPDATE Funcionarios SET Nome = ?, CPF = ?, Cargo = ?, Setor = ?, Situacao = ?, Email = ? WHERE cpf = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, funcionario.getNome());
			
			stmt.setString(2, funcionario.getCpf());
			
			stmt.setInt(3, funcionario.getCargo());
			
			stmt.setInt(4, funcionario.getSetor());
			
			stmt.setString(5, funcionario.getSituacao());
			
			stmt.setString(6, funcionario.getEmail());
			
			stmt.setString(7, funcionario.getCpf());
			
			stmt.executeUpdate();
		}

	}

}