package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.ProdutoDAO;
import com.google.gson.Gson;  // Importando Gson para converter objetos em JSON
import com.model.Produto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/procurarProduto")
public class ProcurarProdutoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pesquisa = request.getParameter("pesquisa");
        System.out.println(pesquisa);
        
        List<Produto> produtos;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection connection = ConnectionFactory.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);

            if (pesquisa != null && !pesquisa.isEmpty()) {
                produtos = produtoDAO.buscarPorNomeOuID(pesquisa);
            } else {
                produtos = produtoDAO.listarTodos();
            }

            Gson gson = new Gson();
            String json = gson.toJson(produtos);

            response.getWriter().write(json);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao acessar o banco de dados\"}");
        }
    }
}
