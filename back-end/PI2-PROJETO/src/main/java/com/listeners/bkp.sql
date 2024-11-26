
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

-- Tabela Tipo_Comercio
DROP TABLE IF EXISTS `Tipo_Comercio`;
CREATE TABLE `Tipo_Comercio` (
  `Id_Comercio` SMALLINT NOT NULL AUTO_INCREMENT,
  `Tipo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id_Comercio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Produto
DROP TABLE IF EXISTS `Produto`;
CREATE TABLE `Produto` (
  `Id_Produto` INT NOT NULL AUTO_INCREMENT,
  `Nome_Produto` VARCHAR(150) NOT NULL,
  `Valor_Produto` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Categoria` SMALLINT NOT NULL,
  `Descricao_Produto` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id_Produto`),
  FOREIGN KEY (`Categoria`) REFERENCES Categoria_Produto (`Id_Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Setor_Funcionario
DROP TABLE IF EXISTS `Setor_Funcionario`;
CREATE TABLE `Setor_Funcionario` (
  `Id_Setor` SMALLINT NOT NULL AUTO_INCREMENT,
  `Setor` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id_Setor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Grupo_Usuario
DROP TABLE IF EXISTS `Grupo_Usuario`;
CREATE TABLE `Grupo_Usuario` (
  `Id_Grupo` TINYINT NOT NULL AUTO_INCREMENT,
  `Grupo` ENUM('Administrador', 'Gerente', 'Supervisor', 'Funcionário', 'Estagiário') NOT NULL,
  PRIMARY KEY (`Id_Grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Fornecedores
DROP TABLE IF EXISTS `Fornecedores`;
CREATE TABLE `Fornecedores` (
  `Id_Fornecedor` INT NOT NULL AUTO_INCREMENT,
  `Nome_Fornecedor` VARCHAR(150) NOT NULL,
  `Tipo_Comercio` SMALLINT NOT NULL,
  `Telefone` VARCHAR(50) NOT NULL,
  `CEP` VARCHAR(10) NOT NULL,
  `Rua` VARCHAR(150) NOT NULL,
  `Bairro` VARCHAR(150) NOT NULL,
  `Estado` VARCHAR(100) NOT NULL,
  `Pais` VARCHAR(100) NOT NULL,
  `Responsavel` VARCHAR(150) NOT NULL,
  `Documento` VARCHAR(50) NOT NULL,
  `Status_Fornecedor` ENUM('Ativo', 'Inativo') NOT NULL,
  PRIMARY KEY (`Id_Fornecedor`),
  FOREIGN KEY (`Tipo_Comercio`) REFERENCES Tipo_Comercio (`Id_Comercio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Funcionarios
DROP TABLE IF EXISTS `Funcionarios`;
	CREATE TABLE `Funcionarios` (
	  `Id_Funcionario` INT NOT NULL AUTO_INCREMENT,
	  `Nome` VARCHAR(100) NOT NULL,
	  `CPF` VARCHAR(14) NOT NULL,
	  `Cargo` SMALLINT NOT NULL,
	  `Setor` SMALLINT NOT NULL,
	  `Situacao` ENUM('Ativo', 'Inativo') NOT NULL,
	  `Email` VARCHAR(100) NOT NULL,
	  PRIMARY KEY (`Id_Funcionario`),
	  FOREIGN KEY (`Cargo`) REFERENCES Cargo_Funcionarios (`Id_Cargo`),
	  FOREIGN KEY (`Setor`) REFERENCES Setor_Funcionario (`Id_Setor`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela Usuario_Interno
DROP TABLE IF EXISTS `Usuario_Interno`;
CREATE TABLE `Usuario_Interno` (
  `Id_Usuario` INT NOT NULL AUTO_INCREMENT,
  `Nome_Usuario` VARCHAR(100) NOT NULL,
  `Senha` VARCHAR(255) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Id_Grupo` TINYINT NOT NULL,
  `Id_Funcionario` INT NOT NULL,
  PRIMARY KEY (`Id_Usuario`),
  FOREIGN KEY (`Id_Grupo`) REFERENCES Grupo_Usuario (`Id_Grupo`),
  FOREIGN KEY (`Id_Funcionario`) REFERENCES Funcionarios (`Id_Funcionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;