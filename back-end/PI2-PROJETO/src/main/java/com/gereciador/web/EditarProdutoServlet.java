package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.ProdutoDAO;
import com.google.gson.Gson;
import com.model.Produto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.stream.Collectors;

@WebServlet("/EditarProdutoGET")
public class EditarProdutoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idProdutoParam = request.getParameter("idProduto");

        if (idProdutoParam == null || idProdutoParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto não fornecido.");
            return;
        }
        

        try (Connection connection = new ConnectionFactory().getConnection()) {
            int idProduto = Integer.parseInt(idProdutoParam);
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);
            Produto produto = produtoDAO.buscarPorId(idProduto);
            System.out.println(idProduto);

            if (produto != null) {
                // Convertendo o produto para JSON
                Gson gson = new Gson();
                String produtoJson = gson.toJson(produto);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(produtoJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jsonString = request.getReader().lines().collect(Collectors.joining());

        if (jsonString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados do produto não fornecidos.");
            return;
        }

        try {
            // Convertendo o JSON recebido para Produto
            Gson gson = new Gson();
            Produto produto = gson.fromJson(jsonString, Produto.class);
            
            System.out.println(produto);

            try (Connection connection = new ConnectionFactory().getConnection()) {
                ProdutoDAO produtoDAO = new ProdutoDAO(connection);
                System.out.println(produto);
                produtoDAO.atualizar(produto); // Atualização sem retorno

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Produto atualizado com sucesso!\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }

    // Função de deletar produto
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idProdutoParam = request.getParameter("idProduto");

        if (idProdutoParam == null || idProdutoParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto não fornecido.");
            return;
        }

        try (Connection connection = new ConnectionFactory().getConnection()) {
            int idProduto = Integer.parseInt(idProdutoParam);
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);
            produtoDAO.deletar(idProduto);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Produto deletado com sucesso!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }
}
