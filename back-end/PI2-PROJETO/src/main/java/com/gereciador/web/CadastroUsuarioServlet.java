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
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/cadastroUsuario")
public class CadastroUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redireciona para o cadastro.html
        request.getRequestDispatcher("/templates/cadastroUsuario.html").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebe os parâmetros do formulário 
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String confirmacaoSenha = request.getParameter("confirmacaoSenha");

        // Verifica se a senha e a confirmação de senha são iguais
        if (!senha.equals(confirmacaoSenha)) {
            // Se as senhas não coincidirem, redireciona de volta com erro
            response.sendRedirect("cadastro.html?error=senhasDiferentes");
            return;
        }

        Connection connection = null;
        try {
            // Cria a conexão com o banco de dados
            connection = new ConnectionFactory().getConnection();

            // Instancia o DAO de Funcionário
            UsuarioDAO funcionarioDAO = new UsuarioDAO(connection);

            // Obtém a lista de IDs de funcionários sem usuário
            List<Integer> funcionariosSemUsuario = funcionarioDAO.listarFuncionariosSemUsuario();

            if (funcionariosSemUsuario.isEmpty()) {
                // Se não houver funcionários sem usuário, retorna erro
                response.sendRedirect("cadastro.html?error=semFuncionarios");
                return;
            }

            // Seleciona o primeiro funcionário sem usuário
            int idFuncionario = funcionariosSemUsuario.get(0);

            // Cria um objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setIdFuncionario(idFuncionario);
            usuario.setDataCriacao(new Timestamp(System.currentTimeMillis()));
            usuario.setUltimoAcesso(new Timestamp(System.currentTimeMillis()));
            usuario.setIdGrupo(1); // Define um grupo padrão, por exemplo.

            // Insere o usuário no banco de dados
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            usuarioDAO.inserir(usuario);

            // Redireciona para a página de sucesso
            response.sendRedirect("cadastroSucesso.html");

        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar ao banco de dados", e);
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
