package com.gereciador.web;

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
import com.gerenciador.dao.UsuarioDAO;
import com.google.gson.Gson;
import com.model.Usuario;

@WebServlet("/edicaoUsuario")
public class EdicaoUsuarioServlet extends HttpServlet {

	
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/templates/edicaoUsuario.html").forward(request, response);
	}*/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idUsuario = request.getParameter("id");

		if (idUsuario != null) {
			try (Connection connection = new ConnectionFactory().getConnection()) {
				UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
				Usuario usuario = usuarioDAO.buscarPorId(Integer.parseInt(idUsuario));

				if (usuario != null) {
					response.setContentType("application/json");
					response.getWriter().write(new Gson().toJson(usuario));
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de número inválido");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro inesperado.");
			}
		} else {
			request.getRequestDispatcher("/templates/edicaoUsuario.html").forward(request, response);
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String jsonString = request.getReader().lines().collect(Collectors.joining());
	    System.out.println("Recebido: " + jsonString);

	    Map<String, String> data = parseJsonToMap(jsonString);

	    String idUsuarioStr = data.get("idUsuario");
	    String nome = data.get("nome");
	    String email = data.get("email");
	    String senha = data.get("senha");

	    System.out.println("ID Usuario: " + idUsuarioStr);
	    System.out.println("Nome: " + nome);
	    System.out.println("Email: " + email);
	    System.out.println("Senha: " + senha);

	    try (Connection connection = new ConnectionFactory().getConnection()) {
	        Integer idUsuario = Integer.parseInt(idUsuarioStr);

	        Usuario usuario = new Usuario();
	        usuario.setIdUsuario(idUsuario);
	        usuario.setNome(nome);
	        usuario.setSenha(senha);
	        usuario.setEmail(email);

	        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
	        usuarioDAO.atualizar(usuario);

	        response.setStatus(HttpServletResponse.SC_OK);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados.");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
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