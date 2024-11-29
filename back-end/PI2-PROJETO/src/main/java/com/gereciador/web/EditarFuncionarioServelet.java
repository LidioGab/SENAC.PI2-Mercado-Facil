package com.gereciador.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.FuncionarioDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.model.Funcionario;

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
				funcionarioDAO.atualizarFuncionario(funcionario);

				response.setStatus(HttpServletResponse.SC_OK);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados.");
			}

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de número inválido");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro inesperado.");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    try {
	        StringBuilder jsonBuffer = new StringBuilder();
	        String line;
	        try (BufferedReader reader = request.getReader()) {
	            while ((line = reader.readLine()) != null) {
	                jsonBuffer.append(line);
	            }
	        }

	        String json = jsonBuffer.toString();
	        if (json.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados do funcionário não fornecidos.");
	            return;
	        }

	        Gson gson = new Gson();
	        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
	        String cpf = jsonObject.get("cpf").getAsString();

	        if (cpf == null || cpf.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CPF do funcionário não fornecido.");
	            return;
	        }

	        try (Connection connection = new ConnectionFactory().getConnection()) {
	            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

	            boolean sucesso = funcionarioDAO.removerFuncionario(cpf);

	            if (sucesso) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("{\"message\": \"Funcionário removido com sucesso.\"}");
	            } else {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Funcionário não encontrado.");
	            }
	        } catch (SQLException e) {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados.");
	        }
	    } catch (JsonSyntaxException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de JSON inválido.");
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