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
	public List<Produto> buscarPorNomeOuID(String pesquisa) throws SQLException {
	    String sql = "SELECT Id_Produto, Nome_Produto, Valor_Produto, Categoria, Descricao_produto " 
	               + "FROM produto "
	               + "WHERE Nome_Produto LIKE ? OR Id_Produto = ?";

	    List<Produto> produtos = new ArrayList<>();

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        // Sempre busca pelo nome do produto
	        stmt.setString(1, "%" + pesquisa + "%");

	        // Tenta buscar pelo ID apenas se a pesquisa for numérica
	        try {
	            int idProduto = Integer.parseInt(pesquisa);
	            stmt.setInt(2, idProduto);  // Se for um número, busca pelo ID
	        } catch (NumberFormatException e) {
	            stmt.setInt(2, -1);  // Valor inválido para ID, então passa um valor que não pode corresponder (por exemplo, -1)
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Produto produto = new Produto(
	                    rs.getInt("Id_Produto"), 
	                    rs.getString("Nome_Produto"),
	                    rs.getDouble("Valor_Produto"), 
	                    rs.getInt("Categoria"), 
	                    rs.getString("Descricao_produto")
	                );
	                produtos.add(produto);
	            }
	        }
	    }
	    return produtos;
	}


	// INSERIR PRODUTO
	public void inserir(Produto produto) throws SQLException {
		String sql = "INSERT INTO produto (Nome_Produto, Valor_Produto, Categoria, Descricao_Produto) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getValorProduto());
			stmt.setInt(3, produto.getCategoria());
			stmt.setString(4, produto.getDescricaoProduto());
			stmt.executeUpdate();
		}
	}

	// ATUALIZAR PRODUTO
	public void atualizar(Produto produto) throws SQLException {
		String sql = "UPDATE produto SET Nome_Produto = ?, Codigo_Barra = ?, Valor_Produto = ?, Categoria = ?, Descricao_Produto = ? WHERE Id_Produto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setDouble(3, produto.getValorProduto());
			stmt.setInt(4, produto.getCategoria());
			stmt.setString(5, produto.getDescricaoProduto());
			stmt.setInt(6, produto.getIdProduto()); // Adiciona o ID do produto para especificar qual produto será
													// atualizado

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
				Produto produto = new Produto(rs.getInt("Id_Produto"), rs.getString("Nome_Produto"),
						rs.getDouble("Valor_Produto"), rs.getInt("Categoria"), rs.getString("Descricao_Produto"));
				produtos.add(produto);
			}
		}

		return produtos;
	}
}