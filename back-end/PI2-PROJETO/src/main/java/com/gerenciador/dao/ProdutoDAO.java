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

    // BUSCAR POR ID
    public Produto buscarPorID(int idProduto) throws SQLException {
        String sql = "SELECT * FROM produto WHERE Id_Produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getInt("Id_Produto"),
                        rs.getString("Nome_Produto"),
                        rs.getInt("Codigo_Barra"),
                        rs.getDouble("Valor_Produto"),
                        rs.getInt("Categoria"),
                        rs.getString("Descricao_Produto")
                    );
                }
            }
        }
        return null;
    }

    // INSERIR PRODUTO
    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (Id_Produto, Nome_Produto, Codigo_Barra, Valor_Produto, Categoria, Descricao_Produto) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produto.getIdProduto());
            stmt.setString(2, produto.getNome());
            stmt.setInt(3, produto.getCodigoBarra());
            stmt.setDouble(4, produto.getValorProduto());
            stmt.setInt(5, produto.getCategoria());
            stmt.setString(6, produto.getDescricaoProduto());

            stmt.executeUpdate();
        }
    }

    // ATUALIZAR PRODUTO
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET Nome_Produto = ?, Codigo_Barra = ?, Valor_Produto = ?, Categoria = ?, Descricao_Produto = ? WHERE Id_Produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getCodigoBarra());
            stmt.setDouble(3, produto.getValorProduto());
            stmt.setInt(4, produto.getCategoria());
            stmt.setString(5, produto.getDescricaoProduto());
            stmt.setInt(6, produto.getIdProduto()); // Adiciona o ID do produto para especificar qual produto será atualizado

            stmt.executeUpdate();
        }
    }

    // DELETAR PRODUTO
    public void deletar(int idProduto) throws SQLException {
        String sql = "DELETE FROM produto WHERE Id_Produto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        }
    }

    // LISTAR TODOS OS PRODUTOS
    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("Id_Produto"),
                    rs.getString("Nome_Produto"),
                    rs.getInt("Codigo_Barra"),
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
