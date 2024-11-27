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

@WebServlet("/EditarFuncionario")
public class EditarFuncionarioServelet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/templates/EditarFuncionario.html").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String email = request.getParameter("email");
		String cpf = request.getParameter("cpf");
		String situacao = request.getParameter("situacao");
		String cargo = request.getParameter("cargo");
		String setor = request.getParameter("setor");

		Connection connection = null;
		try {
			// Cria a conexão com o banco de dados
			connection = new ConnectionFactory().getConnection();

			// Instancia o DAO de Funcionário
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

			// Cria um objeto Funcionario
			Funcionario funcionario = new Funcionario();
//			funcionario.setNome(nome);
//			funcionario.setCpf(cpf);
//			funcionario.setSituacao(situacao);
//			funcionario.setCargo(cargo);
//			funcionario.setEmail(email);
//			funcionario.setSetor(setor);
//			

			funcionarioDAO.atualizar(funcionario);

		} catch (SQLException e) {
			
			throw new ServletException("Erro ao conectar ao banco de dados", e);
			
		} finally {
			
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