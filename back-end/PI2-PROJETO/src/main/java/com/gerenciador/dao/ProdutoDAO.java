package com.gerenciador.dao;

import com.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    // Buscar por nome ou ID
    public List<Produto> buscarPorNomeOuID(String pesquisa) throws SQLException {
        String sql = "SELECT Id_Produto, Nome_Produto, Valor_Produto, Categoria, Descricao_Produto " +
                     "FROM Produto " +
                     "WHERE Nome_Produto LIKE ? OR Id_Produto = ?";

        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + pesquisa + "%");

            try {
                int idProduto = Integer.parseInt(pesquisa);
                stmt.setInt(2, idProduto);
            } catch (NumberFormatException e) {
                stmt.setInt(2, -1); // Caso não seja número
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto(
                        rs.getInt("Id_Produto"),
                        rs.getString("Nome_Produto"),
                        rs.getDouble("Valor_Produto"),
                        rs.getInt("Categoria"),
                        rs.getString("Descricao_Produto")
                    );
                    produtos.add(produto);
                }
            }
        }
        return produtos;
    }

    // Inserir produto
    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produto (Nome_Produto, Valor_Produto, Categoria, Descricao_Produto) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValorProduto());
            stmt.setInt(3, produto.getCategoria());
            stmt.setString(4, produto.getDescricaoProduto());
            stmt.executeUpdate();
        }
    }

    // Atualizar produto
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE Produto SET Nome_Produto = ?, Valor_Produto = ?, Categoria = ?, Descricao_Produto = ? WHERE Id_Produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValorProduto());
            stmt.setInt(3, produto.getCategoria());
            stmt.setString(4, produto.getDescricaoProduto());
            stmt.setInt(5, produto.getIdProduto());
            stmt.executeUpdate();
        }
    }

    // Deletar produto
    public void deletar(int idProduto) throws SQLException {
        String sql = "DELETE FROM Produto WHERE Id_Produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        }
    }

    // Listar todos os produtos
    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Produto";
        List<Produto> produtos = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("Id_Produto"),
                    rs.getString("Nome_Produto"),
                    rs.getDouble("Valor_Produto"),
                    rs.getInt("Categoria"),
                    rs.getString("Descricao_Produto")
                );
                produtos.add(produto);
            }
        }
        return produtos;
    }
}
