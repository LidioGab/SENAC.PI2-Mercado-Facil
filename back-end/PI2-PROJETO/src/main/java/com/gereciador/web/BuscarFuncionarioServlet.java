package com.gereciador.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.google.gson.Gson;
import com.model.Funcionario;

@WebServlet("/BuscarFuncionarioCPF")
public class BuscarFuncionarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpf = request.getParameter("cpf");

        if (cpf == null || cpf.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CPF do funcionário não fornecido.");
            return;
        }

        try (Connection connection = new ConnectionFactory().getConnection()) {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

            Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorCpf(cpf);

            if (funcionario != null) {
                Gson gson = new Gson();
                String funcionarioJson = gson.toJson(funcionario);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(funcionarioJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Funcionário não encontrado.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro inesperado.");
        }
    }
}
