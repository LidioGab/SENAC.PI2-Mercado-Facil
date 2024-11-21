package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.model.Funcionario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.gerenciador.model.Funcionario;

@WebServlet("/EditarFuncionario")
public class EditarFuncionarioServelet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/templates/EditarFuncionario.html").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recebe os parâmetros do formulário
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String email = request.getParameter("email");
		String cpf = request.getParameter("cpf");
		String situacao = request.getParameter("situacao");
		String cargo = request.getParameter("cargo");

		Connection connection = null;
		try {
			// Cria a conexão com o banco de dados
			connection = new ConnectionFactory().getConnection();

			// Instancia o DAO de Funcionário
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

			// Cria um objeto Funcionario
			Funcionario funcionario = new Funcionario();
			funcionario.setNome(nome);
			funcionario.setCpf(cpf);
			funcionario.setSobrenome(sobrenome);
			funcionario.setSituacao(situacao);
			funcionario.setCargo(cargo);
			funcionario.setEmail(email);

			funcionarioDAO.atualizar(funcionario);

			response.sendRedirect("cadastroSucesso.html");

		}


		catch (SQLException e) {
			throw new ServletException("Erro ao conectar ao banco de dados", e);
		} finally {
			// Fecha a conexão com o banco de dados
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
