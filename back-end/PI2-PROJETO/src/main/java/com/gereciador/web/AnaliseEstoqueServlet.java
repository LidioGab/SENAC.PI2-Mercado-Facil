package com.gereciador.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gerenciador.dao.EstoqueDAO;

@WebServlet("/AnaliseEstoque")
public class AnaliseEstoqueServlet extends HttpServlet {

	double totalVendas, rotacaoEstoque = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/templates/AnaliseEstoque.html").forward(request, response);
	}

	protected void doGet2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado_facil", "admin",
				"1234")) {
			EstoqueDAO estoqueDAO = new EstoqueDAO(connection);

			double totalVendas = 0;
			totalVendas = estoqueDAO.calcularValorTotalVendas(0, totalVendas);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"totalVendas\": " + totalVendas + "}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado_facil", "admin",
				"1234")) {
			EstoqueDAO estoqueDAO = new EstoqueDAO(connection);
			double rotacaoEstoque = 0;
			rotacaoEstoque = estoqueDAO.calcularRotacaoEstoque(0, rotacaoEstoque);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"rotacaoEstoque\": " + rotacaoEstoque + "}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado_facil", "admin",
				"1234")) {
			EstoqueDAO estoqueDAO = new EstoqueDAO(connection);
			double totalVendas = 0;
			totalVendas = estoqueDAO.calcularValorTotalVendas(0, totalVendas);
			System.out.printf("O valor total de vendas é: R$ %.2f%n", totalVendas);
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/templates/AnaliseEstoque.html").forward(request, response);
	}

}
