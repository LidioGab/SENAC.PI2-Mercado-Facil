package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.model.Funcionario;
import com.google.gson.Gson;  // Importando Gson para converter objetos em JSON

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/procurarFuncionario")
public class ProcurarFuncionarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pesquisa = request.getParameter("pesquisa");
        System.out.println("Parâmetro de pesquisa recebido: " + pesquisa);

        List<Funcionario> funcionarios;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection connection = ConnectionFactory.getConnection()) {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

            if (pesquisa != null && !pesquisa.isEmpty()) {
                funcionarios = funcionarioDAO.buscarPorNomeOuID(pesquisa);
            } else {
                funcionarios = funcionarioDAO.listarTodos();
            }

            Gson gson = new Gson();
            String json = gson.toJson(funcionarios);

            response.getWriter().write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao acessar o banco de dados\"}");
        }
    }
}
