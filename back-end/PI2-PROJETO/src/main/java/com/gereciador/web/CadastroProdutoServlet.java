package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.ProdutoDAO;
import com.model.Produto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet("/cadastrarProduto")
public class CadastroProdutoServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.getRequestDispatcher("/templates/CadastrarProduto.html").forward(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String jsonString = request.getReader().lines().collect(Collectors.joining());
	        System.out.println("Recebido: " + jsonString);

	        Map<String, String> data = parseJsonToMap(jsonString);

	        String nome = data.get("nome");
	        String valors = data.get("valor");
	        String categorias = data.get("categoria");
	        String descricao = data.get("descricao");
	        
	        
	        try {
	        	double valor = Double.parseDouble(valors);
	        	int categoria = Integer.parseInt(categorias);
	        	
	        	 try (Connection connection = new ConnectionFactory().getConnection()){
	        		 Produto produto = new Produto();
	        		 
	        		 produto.setNome(nome);
	        		 produto.setValorProduto(valor);
	        		 produto.setCategoria(categoria);
	        		 produto.setDescricaoProduto(descricao);
	        		 
	        		 
	        		 ProdutoDAO ProdutoDAO = new ProdutoDAO(connection);
	        		 ProdutoDAO.inserir(produto); 
	        		 
	        		
	        		 
	        	 }
	        }catch (NumberFormatException e) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do funcionário inválido.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro inesperado.");
	        }
	    }



	    private Map<String, String> parseJsonToMap(String jsonString) {
	        Map<String, String> data = new HashMap<>();
	        jsonString = jsonString.trim();

	        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
	            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();
	        }
	        String[] keyValuePairs = jsonString.split(",");

	        for (String pair : keyValuePairs) {
	            String[] keyValue = pair.split(":");
	            if (keyValue.length == 2) {
	                String key = keyValue[0].trim().replace("\"", "");
	                String value = keyValue[1].trim().replace("\"", "");
	                data.put(key, value);
	            }
	        }
	        return data;
	    }
}