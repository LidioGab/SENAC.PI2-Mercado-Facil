package com.gereciador.web;

import com.gerenciador.dao.ConnectionFactory;
import com.gerenciador.dao.UsuarioDAO;
import com.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/cadastroUsuario")
public class CadastroUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Método GET para redirecionar para o formulário de cadastro
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redireciona para o cadastroUsuario.html
        request.getRequestDispatcher("/templates/cadastroUsuario.html").forward(request, response);
    }

    // Método POST para processar os dados do formulário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configura o encoding para evitar problemas com caracteres especiais
        request.setCharacterEncoding("UTF-8");

        // Lê o corpo da requisição como uma string JSON
        String jsonString = request.getReader().lines().collect(Collectors.joining());
        System.out.println("Recebido: " + jsonString);

        // Converte o JSON para um mapa de parâmetros
        Map<String, String> data = parseJsonToMap(jsonString);

        // Obtém os parâmetros a partir do mapa (agora extraídos do JSON)
        String nome = data.get("nome");
        String email = data.get("email");
        String senha = data.get("senha");
        String idFuncionarioStr = data.get("idFuncionario");
        String grupo = data.get("grupo");

        // Verifica se todos os campos obrigatórios foram preenchidos
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty() || idFuncionarioStr == null || idFuncionarioStr.isEmpty() || grupo == null || grupo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos são obrigatórios.");
            return;
        }

        try {
            int idFuncionario = Integer.parseInt(idFuncionarioStr);

            try (Connection connection = new ConnectionFactory().getConnection()) {
                // Cria objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senha);
                usuario.setIdFuncionario(idFuncionario);
                usuario.setIdGrupo(grupo); // "grupo" é uma string

                // Insere o usuário no banco de dados
                UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
                usuarioDAO.inserir(usuario);

                // Redireciona para a página de sucesso
                response.sendRedirect("cadastroSucesso.html");
            } catch (SQLException e) {
                throw new ServletException("Erro ao conectar ao banco de dados.", e);
            }

        } catch (NumberFormatException e) {
            // Trata o erro caso o ID do funcionário não seja um número válido
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do funcionário inválido.");
        } catch (Exception e) {
            // Trata outros erros inesperados
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro inesperado.");
        }
    }

    // Método para converter o JSON em um mapa de dados
    private Map<String, String> parseJsonToMap(String jsonString) {
        Map<String, String> data = new HashMap<>();
        jsonString = jsonString.trim();

        // Remove as chaves do JSON
        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();
        }

        // Divide o JSON em pares chave-valor
        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {
            // Divide chave e valor (presumindo que são separados por ":")
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                // Remove aspas e espaços em branco
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim().replace("\"", "");
                data.put(key, value);
            }
        }
        return data;
    }
}
