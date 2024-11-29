package com.gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Funcionario;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionarios (Nome, CPF, Cargo, Setor, Situacao, Email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setInt(3, funcionario.getCargo());
            stmt.setInt(4, funcionario.getSetor());
            stmt.setString(5, funcionario.getSituacao());
            stmt.setString(6, funcionario.getEmail());
            stmt.executeUpdate();
        }
    }
    
	    public boolean removerFuncionario(String cpf) throws SQLException {
	        String sql = "DELETE FROM funcionarios WHERE CPF = ?"; 
	        
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, cpf); 
	            int rowsAffected = statement.executeUpdate();
	            return rowsAffected > 0; 
	        }
	    }
	    
	    public Funcionario buscarFuncionarioPorCpf(String cpf) throws SQLException {
	        String sql = "SELECT * FROM funcionarios WHERE CPF = ?";

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, cpf);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    Funcionario funcionario = new Funcionario();
	                    funcionario.setId(resultSet.getInt("Id_Funcionario"));
	                    funcionario.setNome(resultSet.getString("Nome"));
	                    funcionario.setCpf(resultSet.getString("CPF"));
	                    funcionario.setEmail(resultSet.getString("Email"));
	                    funcionario.setCargo(resultSet.getInt("Cargo"));
	                    funcionario.setSetor(resultSet.getInt("Setor"));
	                    funcionario.setSituacao(resultSet.getString("Situacao"));
	                    return funcionario;
	                }
	            }
	        }
	        return null;
	    }
	    
	    
    public List<Funcionario> buscarPorNomeOuID(String pesquisa) throws SQLException {
        String sql = "SELECT Id_Funcionario, Nome, CPF, Cargo, Setor, Situacao, Email "
                   + "FROM Funcionarios "
                   + "WHERE Nome LIKE ? OR Id_Funcionario = ?";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + pesquisa + "%");
            try {
                int idFuncionario = Integer.parseInt(pesquisa);
                stmt.setInt(2, idFuncionario);
            } catch (NumberFormatException e) {
                stmt.setInt(2, -1);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("Id_Funcionario"));
                    funcionario.setNome(rs.getString("Nome"));
                    funcionario.setCpf(rs.getString("CPF"));
                    funcionario.setCargo(rs.getInt("Cargo"));
                    funcionario.setSetor(rs.getInt("Setor"));
                    funcionario.setSituacao(rs.getString("Situacao"));
                    funcionario.setEmail(rs.getString("Email"));
                    funcionarios.add(funcionario);
                }
            }
        }
        return funcionarios;
    }

    public List<Funcionario> listarTodos() throws SQLException {
        String sql = "SELECT Id_Funcionario, Nome, CPF, Cargo, Setor, Situacao, Email FROM Funcionarios";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("Id_Funcionario"));
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setCpf(rs.getString("CPF"));
                funcionario.setCargo(rs.getInt("Cargo"));
                funcionario.setSetor(rs.getInt("Setor"));
                funcionario.setSituacao(rs.getString("Situacao"));
                funcionario.setEmail(rs.getString("Email"));
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionarios SET Nome = ?, Cargo = ?, Setor = ?, Situacao = ?, Email = ? WHERE CPF = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getCargo());
            stmt.setInt(3, funcionario.getSetor());
            stmt.setString(4, funcionario.getSituacao());
            stmt.setString(5, funcionario.getEmail());
            stmt.setString(6, funcionario.getCpf());
            stmt.executeUpdate();
        }
    }
}
