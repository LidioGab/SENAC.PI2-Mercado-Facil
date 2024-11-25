package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.UsuarioDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/funcionariosSemUsuario")
public class FuncionariosSemUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        try {
            // Cria a conexão com o banco de dados
            connection = new ConnectionFactory().getConnection();

            // Instancia o DAO de Usuário
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);

            // Obtém a lista de IDs de funcionários sem usuário
            List<Integer> funcionariosSemUsuario = usuarioDAO.listarFuncionariosSemUsuario();

            if (funcionariosSemUsuario == null || funcionariosSemUsuario.isEmpty()) {
                // Retorna erro 404 se não encontrar funcionários sem usuário
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Nenhum funcionário encontrado.\"}");
                return;
            }

            // Converte a lista de IDs em JSON usando Gson
            Gson gson = new Gson();
            String json = gson.toJson(funcionariosSemUsuario);

            // Define o tipo de resposta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Escreve a resposta JSON
            response.getWriter().write(json);

        } catch (SQLException e) {
            // Caso ocorra um erro no banco de dados, retorna erro 500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao conectar ao banco de dados.\"}");
            e.printStackTrace();
        } catch (Exception e) {
            // Captura qualquer outro tipo de exceção e retorna erro 500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro inesperado.\"}");
            e.printStackTrace();
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
