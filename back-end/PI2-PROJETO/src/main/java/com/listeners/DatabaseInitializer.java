package com.listeners;

import com.gerenciador.dao.ConnectionFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Lógica de verificação e criação do banco de dados
        try (Connection conn = ConnectionFactory.getConnectionWithoutDatabase()) {
            Statement stmt = conn.createStatement();

            // Verificar se o banco de dados já existe
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS mercado_facil");
            LOGGER.info("Database mercado_facil created or already exists.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating database", e);
        }

        // Usar a conexão com o banco de dados para o restante das operações
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement();

            // Usar o banco de dados
            stmt.executeUpdate("USE mercado_facil");

            // Executar script SQL para criar tabelas
            String sqlFile = "/com/listeners/bkp.sql"; // Caminho relativo ao pacote
            executeSQLFile(conn, sqlFile);

            // Verificar se o banco de dados está vazio
            if (isDatabaseEmpty(conn)) {
                LOGGER.info("Database is empty. Inserting initial data...");
                insertInitialData(conn);
            } else {
                LOGGER.info("Database is not empty. No data inserted.");
            }
        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error initializing database", e);
        }
    }

    private void executeSQLFile(Connection conn, String file) throws SQLException, IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file)))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sb.append(line);
                if (line.endsWith(";")) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sb.toString());
                    }
                    sb.setLength(0);
                }
            }
        }
    }

    private boolean isDatabaseEmpty(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Produto"; // Verifica se a tabela Produto está vazia
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) == 0; // Se o count for 0, o banco está vazio
            }
        }
        return false;
    }

    private void insertInitialData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Inserir dados nas tabelas sem dependência de chave estrangeira
            statement.executeUpdate("INSERT INTO Grupo_Usuario (Grupo) VALUES ('Administrador'), ('Gerente'), ('Supervisor'), ('Funcionário'), ('Estagiário')");
            LOGGER.info("Inserted data into Grupo_Usuario");

            statement.executeUpdate("INSERT INTO Cargo_Funcionarios (Cargo) VALUES ('Gerente'), ('Vendedor'), ('Caixa'), ('Estoquista'), ('Assistente'), ('Supervisor'), ('Assistente Administrativo'), ('Administrador')");
            LOGGER.info("Inserted data into Cargo_Funcionarios");

            statement.executeUpdate("INSERT INTO Setor_Funcionario (Setor) VALUES ('Vendas'), ('Financeiro'), ('Estoques'), ('Administração'), ('Marketing'), ('Recursos Humanos'), ('Logística')");
            LOGGER.info("Inserted data into Setor_Funcionario");

            statement.executeUpdate("INSERT INTO Categoria_Produto (Categoria, Sub_Categoria) VALUES ('Eletrônicos', 'Celulares'), ('Alimentos', 'Bebidas'), ('Vestuário', 'Roupas')");
            LOGGER.info("Inserted data into Categoria_Produto");

            statement.executeUpdate("INSERT INTO Tipo_Comercio (Tipo) VALUES ('Varejo'), ('Atacado'), ('E-commerce')");
            LOGGER.info("Inserted data into Tipo_Comercio");

            // Inserir dados na tabela Produto
            statement.executeUpdate("INSERT INTO Produto (Nome_Produto, Codigo_Barra, Valor_Produto, Categoria, Descricao_Produto) VALUES " +
                    "('Smartphone XYZ', '1234567890123', 1500.00, 1, 'Smartphone de última geração')," +
                    "('Refrigerante ABC', '9876543210987', 5.00, 2, 'Refrigerante sabor cola')");
            LOGGER.info("Inserted data into Produto");

            statement.executeUpdate("INSERT INTO Fornecedores (Nome_Fornecedor, Tipo_Comercio, Telefone, CEP, Rua, Bairro, Estado, Pais, Responsavel, Documento, Status_Fornecedor) VALUES " +
                    "('Fornecedor A', 1, '123456789', '12345-678', 'Rua Exemplo', 'Bairro A', 'Estado A', 'Brasil', 'João Silva', '1234567890', 'Ativo')");
            LOGGER.info("Inserted data into Fornecedores");

            // Agora inserindo nas tabelas que dependem de chave estrangeira
            statement.executeUpdate("INSERT INTO Funcionarios (Nome, Sobrenome, CPF, Cargo, Setor, Situacao, Email) VALUES " +
                    "('João', 'Silva', '123.456.789-00', 1, 1, 'Ativo', 'joao.silva@example.com')," +
                    "('Maria', 'Oliveira', '987.654.321-00', 2, 2, 'Ativo', 'maria.oliveira@example.com')");
            LOGGER.info("Inserted data into Funcionarios");

            statement.executeUpdate("INSERT INTO Usuario_Interno (Nome_Usuario, Senha, Email, Id_Grupo, Id_Funcionario) VALUES " +
                    "('admin', 'admin123', 'admin@example.com', 1, 1)");
            LOGGER.info("Inserted data into Usuario_Interno");

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Opcional: lógica a ser executada quando a aplicação for finalizada
        try {
            ConnectionFactory.closeAllConnections(); // Fecha todas as conexões abertas
            LOGGER.info("All connections closed.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing connections", e);
        }
    }
}
