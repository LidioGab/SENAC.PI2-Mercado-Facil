CREATE DATABASE IF NOT EXISTS `mercado_facil` 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_general_ci;

USE `mercado_facil`;

-- Tabela Cargo_Funcionarios
DROP TABLE IF EXISTS `Cargo_Funcionarios`;
CREATE TABLE `Cargo_Funcionarios` (
  `Id_Cargo` SMALLINT NOT NULL AUTO_INCREMENT,
  `Cargo` ENUM('Gerente', 'Vendedor', 'Caixa', 'Estoquista', 'Assistente', 'Supervisor', 'Assistente Administrativo', 'Administrador') NOT NULL,
  PRIMARY KEY (`Id_Cargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Categoria_Produto
DROP TABLE IF EXISTS `Categoria_Produto`;
CREATE TABLE `Categoria_Produto` (
  `Id_Categoria` SMALLINT NOT NULL AUTO_INCREMENT,
  `Categoria` VARCHAR(75) NOT NULL,
  `Sub_Categoria` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id_Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Fornecedores
DROP TABLE IF EXISTS `Fornecedores`;
CREATE TABLE `Fornecedores` (
  `Id_Fornecedor` INT NOT NULL AUTO_INCREMENT,
  `Nome_Fornecedor` VARCHAR(150) NOT NULL,
  `Tipo_Comercio` SMALLINT NOT NULL,
  `Telefone` VARCHAR(20) DEFAULT NULL,
  `CEP` VARCHAR(12) NOT NULL,
  `Rua` VARCHAR(200) NOT NULL,
  `Bairro` VARCHAR(50) NOT NULL,
  `Estado` VARCHAR(40) NOT NULL,
  `Pais` VARCHAR(50) NOT NULL DEFAULT 'Brasil',
  `Responsavel` VARCHAR(50) NOT NULL,
  `Documento` VARCHAR(50) NOT NULL,
  `Status_Fornecedor` ENUM('Ativo','Inativo','Suspenso','Em Avaliação') NOT NULL DEFAULT 'Ativo',
  PRIMARY KEY (`Id_Fornecedor`),
  KEY `fk_tipo_comercio` (`Tipo_Comercio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Tipo_Comercio
DROP TABLE IF EXISTS `Tipo_Comercio`;
CREATE TABLE `Tipo_Comercio` (
  `Id_Comercio` SMALLINT NOT NULL AUTO_INCREMENT,
  `Tipo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id_Comercio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Compra
DROP TABLE IF EXISTS `Compra`;
CREATE TABLE `Compra` (
  `Id_Compra` INT NOT NULL AUTO_INCREMENT,
  `Valor_Unitario` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Unidade_Medida` VARCHAR(50) NOT NULL,
  `Valor_Total` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Qtde_Total` INT NOT NULL,
  `Id_Fornecedor` INT NOT NULL,
  `Previsao_Entrega` DATE NOT NULL,
  `Status_Entrega` ENUM('Pendente','Em Transito','Concluída','Cancelada') NOT NULL DEFAULT 'Pendente',
  PRIMARY KEY (`Id_Compra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Controle_Acesso_Usuario
DROP TABLE IF EXISTS `Controle_Acesso_Usuario`;
CREATE TABLE `Controle_Acesso_Usuario` (
  `Id_Log` BIGINT NOT NULL AUTO_INCREMENT,
  `Id_Usuario` INT NOT NULL,
  `Horario_Login` DATETIME NOT NULL,
  `Horario_Logoff` DATETIME NOT NULL,
  PRIMARY KEY (`Id_Log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Empresa
DROP TABLE IF EXISTS `Empresa`;
CREATE TABLE `Empresa` (
  `Id_Empresa` INT NOT NULL DEFAULT '1',
  `Razao_Social` VARCHAR(200) NOT NULL,
  `CNPJ` VARCHAR(50) NOT NULL,
  `CEP` VARCHAR(12) NOT NULL,
  `Rua` VARCHAR(200) NOT NULL,
  `Bairro` VARCHAR(50) NOT NULL,
  `Estado` VARCHAR(50) NOT NULL,
  `Pais` VARCHAR(50) NOT NULL DEFAULT 'Brasil',
  `Telefone` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Id_Empresa`),
  UNIQUE KEY `CNPJ` (`CNPJ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Estoque
DROP TABLE IF EXISTS `Estoque`;
CREATE TABLE `Estoque` (
  `Id_Estoque` INT NOT NULL AUTO_INCREMENT,
  `Id_Produto` INT NOT NULL,
  `Quantidade` INT NOT NULL,
  `Data_Validade` DATE DEFAULT NULL,
  `Data_Solicitacao` DATE NOT NULL,
  `Data_Entrada` DATE DEFAULT NULL,
  `Custo` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Id_Fornecedor` INT NOT NULL,
  `Status_estoque` ENUM('Disponível','Esgotado','Em Reposição') NOT NULL DEFAULT 'Em Reposição',
  PRIMARY KEY (`Id_Estoque`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Funcionarios
DROP TABLE IF EXISTS `Funcionarios`;
CREATE TABLE `Funcionarios` (
  `Id_Funcionario` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Sobrenome` VARCHAR(150) NOT NULL,
  `CPF` VARCHAR(20) NOT NULL,
  `Cargo` SMALLINT NOT NULL,
  `Setor` SMALLINT DEFAULT NULL,
  `Situacao` ENUM('Ativo','Inativo','Desligado','Afastado','Suspenso') NOT NULL DEFAULT 'Ativo',
  `Email` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`Id_Funcionario`),
  UNIQUE KEY `CPF` (`CPF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Grupo_Usuario
DROP TABLE IF EXISTS `Grupo_Usuario`;
CREATE TABLE `Grupo_Usuario` (
  `Id_Grupo` SMALLINT NOT NULL AUTO_INCREMENT,
  `Grupo` ENUM('Administrador','Gerente','Supervisor','Funcionário','Estagiário') NOT NULL,
  PRIMARY KEY (`Id_Grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Produto
DROP TABLE IF EXISTS `Produto`;
CREATE TABLE `Produto` (
  `Id_Produto` INT NOT NULL AUTO_INCREMENT,
  `Nome_Produto` VARCHAR(150) NOT NULL,
  `Codigo_Barra` VARCHAR(50) NOT NULL,
  `Valor_Produto` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Categoria` SMALLINT NOT NULL,
  `Descricao_Produto` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id_Produto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Setor_Funcionario
DROP TABLE IF EXISTS `Setor_Funcionario`;
CREATE TABLE `Setor_Funcionario` (
  `Id_Setor` SMALLINT NOT NULL AUTO_INCREMENT,
  `Setor` ENUM('Vendas','Financeiro','Estoques','Administração','Marketing','Recursos Humanos','Logística') NOT NULL,
  PRIMARY KEY (`Id_Setor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Usuario_Interno
DROP TABLE IF EXISTS `Usuario_Interno`;
CREATE TABLE `Usuario_Interno` (
  `Id_Usuario` INT NOT NULL AUTO_INCREMENT,
  `Nome_Usuario` VARCHAR(100) NOT NULL,
  `Senha` VARCHAR(200) NOT NULL,
  `Email` VARCHAR(200) NOT NULL,
  `Data_Criacao` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Ultimo_Acesso` DATETIME DEFAULT NULL,
  `Id_Grupo` SMALLINT NOT NULL,
  `Id_Funcionario` INT NOT NULL,
  PRIMARY KEY (`Id_Usuario`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Adicionando Constraints com ALTER TABLE

ALTER TABLE `Compra`
  ADD CONSTRAINT `fk_fornecedor_compra` FOREIGN KEY (`Id_Fornecedor`) REFERENCES `Fornecedores` (`Id_Fornecedor`);

ALTER TABLE `Controle_Acesso_Usuario`
  ADD CONSTRAINT `fk_usuario_acesso` FOREIGN KEY (`Id_Usuario`) REFERENCES `Usuario_Interno` (`Id_Usuario`);

ALTER TABLE `Estoque`
  ADD CONSTRAINT `fk_produto_estoque` FOREIGN KEY (`Id_Produto`) REFERENCES `Produto` (`Id_Produto`),
  ADD CONSTRAINT `fk_fornecedor_estoque` FOREIGN KEY (`Id_Fornecedor`) REFERENCES `Fornecedores` (`Id_Fornecedor`);

ALTER TABLE `Funcionarios`
  ADD CONSTRAINT `fk_cargo` FOREIGN KEY (`Cargo`) REFERENCES `Cargo_Funcionarios` (`Id_Cargo`),
  ADD CONSTRAINT `fk_setor` FOREIGN KEY (`Setor`) REFERENCES `Setor_Funcionario` (`Id_Setor`);

ALTER TABLE `Usuario_Interno`
  ADD CONSTRAINT `fk_grupo_usuario` FOREIGN KEY (`Id_Grupo`) REFERENCES `Grupo_Usuario` (`Id_Grupo`),
  ADD CONSTRAINT `fk_funcionario_usuario` FOREIGN KEY (`Id_Funcionario`) REFERENCES `Funcionarios` (`Id_Funcionario`);

ALTER TABLE `Fornecedores`
  ADD CONSTRAINT `fk_tipo_comercio_fornecedor` FOREIGN KEY (`Tipo_Comercio`) REFERENCES `Tipo_Comercio` (`Id_Comercio`);

ALTER TABLE `Produto`
  ADD CONSTRAINT `fk_categoria_produto` FOREIGN KEY (`Categoria`) REFERENCES `Categoria_Produto` (`Id_Categoria`);
