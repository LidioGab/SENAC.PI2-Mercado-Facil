package com.gerenciador.dao;

import com.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection	 connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
 // Método para retornar lista de IDs de funcionários que não estão relacionados a usuarios_internos
    public List<Integer> listarFuncionariosSemUsuario() throws SQLException {
        String sql = "SELECT f.Id_Funcionario FROM Funcionarios f " +
                     "LEFT JOIN Usuario_Interno u ON f.Id_Funcionario = u.Id_Funcionario " +
                     "WHERE u.Id_Funcionario IS NULL";

        List<Integer> funcionariosSemUsuario = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionariosSemUsuario.add(rs.getInt("Id_Funcionario"));
            }
        }

        return funcionariosSemUsuario;
    }
    
 // Método para buscar usuário por email e senha
    public Usuario buscarPorEmailSenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario_interno WHERE Email = ? AND Senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("Id_Usuario"),
                        rs.getString("Nome_Usuario"),
                        rs.getString("Senha"),
                        rs.getString("Email"),
                        rs.getTimestamp("Data_Criacao"),
                        rs.getTimestamp("Ultimo_Acesso"),
                        rs.getInt("Id_Grupo"),
                        rs.getInt("Id_Funcionario")
                    );
                }
            }
        }
        return null; // Se não encontrar o usuário
    }
    
    // Método para inserir um novo usuário
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario_interno (Nome_Usuario, Senha, Email, Data_Criacao, Ultimo_Acesso, Id_Grupo, Id_Funcionario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getEmail());
            stmt.setTimestamp(4, new Timestamp(usuario.getDataCriacao().getTime()));
            stmt.setTimestamp(5, new Timestamp(usuario.getUltimoAcesso().getTime()));
            stmt.setInt(6, usuario.getIdGrupo());
            stmt.setInt(7, usuario.getIdFuncionario());
            stmt.executeUpdate();
        }
    }

    // Método para buscar um usuário por ID
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario_interno WHERE Id_Usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("Id_Usuario"),
                        rs.getString("Nome_Usuario"),
                        rs.getString("Senha"),
                        rs.getString("Email"),
                        rs.getTimestamp("Data_Criacao"),
                        rs.getTimestamp("Ultimo_Acesso"),
                        rs.getInt("Id_Grupo"),
                        rs.getInt("Id_Funcionario")
                    );
                }
            }
        }
        return null;
    }

    // Método para atualizar um usuário existente
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario_interno SET Nome_Usuario = ?, Senha = ?, Email = ?, Ultimo_Acesso = ?, Id_Grupo = ?, Id_Funcionario = ? WHERE Id_Usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getEmail());
            stmt.setTimestamp(4, new Timestamp(usuario.getUltimoAcesso().getTime()));
            stmt.setInt(5, usuario.getIdGrupo());
            stmt.setInt(6, usuario.getIdFuncionario());
            stmt.setInt(7, usuario.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um usuário pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario_interno WHERE Id_Usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os usuários
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuario_interno";
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("Id_Usuario"),
                    rs.getString("Nome_Usuario"),
                    rs.getString("Senha"),
                    rs.getString("Email"),
                    rs.getTimestamp("Data_Criacao"),
                    rs.getTimestamp("Ultimo_Acesso"),
                    rs.getInt("Id_Grupo"),
                    rs.getInt("Id_Funcionario")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}
