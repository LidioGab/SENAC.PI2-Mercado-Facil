package com.gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Estoque;

public class EstoqueDAO {
	private Connection connection;

	public EstoqueDAO(Connection connection) {
		this.connection = connection;
	}

	public void create(Estoque estoque) throws SQLException {
		String sql = "INSERT INTO estoque (id_produto, quantidade, data_validade, data_solicitacao, data_entrada, custo, id_fornecedor) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, estoque.getIdProduto());
			stmt.setInt(2, estoque.getQuantidade());
			stmt.setDate(3, new java.sql.Date(estoque.getDataValidade().getTime()));
			stmt.setDate(4, new java.sql.Date(estoque.getDataSolicitacao().getTime()));
			stmt.setDate(5, new java.sql.Date(estoque.getDataEntrada().getTime()));
			stmt.setDouble(6, estoque.getCusto());
			stmt.setInt(7, estoque.getIdFornecedor());
			stmt.executeUpdate();
		}
	}

	public Estoque read(Integer idEstoque) throws SQLException {
		String sql = "SELECT * FROM estoque WHERE id_estoque = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idEstoque);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Estoque estoque = new Estoque();
					estoque.setIdEstoque(rs.getInt("id_estoque"));
					estoque.setIdProduto(rs.getInt("id_produto"));
					estoque.setQuantidade(rs.getInt("quantidade"));
					estoque.setDataValidade(rs.getDate("data_validade"));
					estoque.setDataSolicitacao(rs.getDate("data_solicitacao"));
					estoque.setDataEntrada(rs.getDate("data_entrada"));
					estoque.setCusto(rs.getDouble("custo"));
					estoque.setIdFornecedor(rs.getInt("id_fornecedor"));
					return estoque;
				}
			}
		}
		return null;
	}

	public void update(Estoque estoque) throws SQLException {
		String sql = "UPDATE estoque SET id_produto = ?, quantidade = ?, data_validade = ?, data_solicitacao = ?, data_entrada = ?, custo = ?, id_fornecedor = ? WHERE id_estoque = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, estoque.getIdProduto());
			stmt.setInt(2, estoque.getQuantidade());
			stmt.setDate(3, new java.sql.Date(estoque.getDataValidade().getTime()));
			stmt.setDate(4, new java.sql.Date(estoque.getDataSolicitacao().getTime()));
			stmt.setDate(5, new java.sql.Date(estoque.getDataEntrada().getTime()));
			stmt.setDouble(6, estoque.getCusto());
			stmt.setInt(7, estoque.getIdFornecedor());
			stmt.setInt(8, estoque.getIdEstoque());
			stmt.executeUpdate();
		}
	}

	public void delete(Integer idEstoque) throws SQLException {
		String sql = "DELETE FROM estoque WHERE id_estoque = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idEstoque);
			stmt.executeUpdate();
		}
	}

	public double calcularValorTotalVendas(int quantidade, double custo) throws SQLException {
		String sql = "SELECT SUM(quantidade * custo) AS total_vendas FROM estoque";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return quantidade * custo;
			}
		}
		return 0.0;
	}

	public double calcularSaldoFisicoEstoque(int quantidade) throws SQLException {
		String sql = "SELECT SUM(quantidade) AS saldo_fisico FROM estoque";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return quantidade;
			}
		}
		return 0.0;
	}

	public double calcularValorMercadoriaEstoque(int quantidade, double custo) throws SQLException {
		String sql = "SELECT SUM(quantidade * custo) AS valor_mercadoria FROM estoque";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return quantidade * custo;
			}
		}
		return 0.0;
	}

	public double calcularReceitaEstoque(int quantidade, double custo) throws SQLException {
		String sql = "SELECT SUM(quantidade * custo) AS receita_estoque FROM estoque";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return quantidade * custo;
			}
		}
		return 0.0;
	}

	public double calcularRotacaoEstoque(int quantidade, double custo) throws SQLException {
		String sql = "SELECT (SUM(quantidade * custo) / AVG(quantidade)) AS rotacao_estoque FROM estoque";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return (quantidade * custo) / quantidade;
			}
		}
		return 0.0;
	}

	public Estoque buscarProdutoPorId(int idProduto) throws SQLException {
		String sql = "SELECT * FROM estoque WHERE id_produto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idProduto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Estoque estoque = new Estoque();
					estoque.setIdEstoque(rs.getInt("id_estoque"));
					estoque.setIdProduto(rs.getInt("id_produto"));
					estoque.setQuantidade(rs.getInt("quantidade"));
					estoque.setDataValidade(rs.getDate("data_validade"));
					estoque.setDataSolicitacao(rs.getDate("data_solicitacao"));
					estoque.setDataEntrada(rs.getDate("data_entrada"));
					estoque.setCusto(rs.getDouble("custo"));
					estoque.setIdFornecedor(rs.getInt("id_fornecedor"));
					return estoque;
				}
			}
		}
		return null;
	}

}
