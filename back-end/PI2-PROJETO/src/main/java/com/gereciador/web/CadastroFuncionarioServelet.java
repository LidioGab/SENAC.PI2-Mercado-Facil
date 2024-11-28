package com.gereciador.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.model.Funcionario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/CadastroFuncionario")
public class CadastroFuncionarioServelet extends HttpServlet {
  
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/templates/CadastroFuncionario.html").forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String jsonString = request.getReader().lines().collect(Collectors.joining());
        System.out.println("Recebido: " + jsonString);
        
        Map<String, String> data = parseJsonToMap(jsonString);
        
        
        String nome = data.get("nome");
        String cpf = data.get("cpf");
        String cargoStr = data.get("cargo");
        String setorStr = data.get("setor");
        String situacao = data.get("situacao");
        String email = data.get("email");
        
        try {
            int cargo = Integer.parseInt(cargoStr);
            int setor = Integer.parseInt(setorStr);
            
            try (Connection connection = new ConnectionFactory().getConnection()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setCargo(cargo);
                funcionario.setSetor(setor);
                funcionario.setSituacao(situacao);
                funcionario.setEmail(email);
                
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
                funcionarioDAO.cadastrarFuncionario(funcionario);
                
            } catch (SQLException e) {
                throw new ServletException("Erro ao conectar ao banco de dados.", e);
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de número inválido");
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