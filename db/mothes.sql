-- phpMyAdmin SQL Dump
-- version 6.0.0-dev+20250812.7569edf1cc
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 02, 2025 at 09:46 AM
-- Server version: 9.5.0
-- PHP Version: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mothes`
--

-- --------------------------------------------------------

--
-- Table structure for table `certificado`
--

CREATE TABLE `certificado` (
  `idCertificado` int NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `horasCreditadas` int DEFAULT NULL,
  `dataConclusao` date DEFAULT NULL,
  `codigoAutenticidade` varchar(100) DEFAULT NULL,
  `linkVerificacao` varchar(255) DEFAULT NULL,
  `caminhoCertificado` varchar(255) DEFAULT NULL,
  `statusValidacao` enum('pendente','validado','rejeitado') DEFAULT NULL,
  `tipoCertificacao` varchar(45) DEFAULT NULL,
  `usuario_idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `certificado_recompensa`
--

CREATE TABLE `certificado_recompensa` (
  `certificado_idCertificado` int NOT NULL,
  `recompensaCert_idrecompensaCert` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cosmetico`
--

CREATE TABLE `cosmetico` (
  `idcosmetico` int NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `preco` int DEFAULT NULL,
  `uso` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cosmetico`
--

INSERT INTO `cosmetico` (`idcosmetico`, `nome`, `tipo`, `image`, `preco`, `uso`) VALUES
(1, 'Silly Hat', 'cabeca', 'mothes/assets/hats/sillyhat.png', 10, 'acessorio'),
(2, 'Scania Hat', 'cabeca', 'mothes/assets/hats/scania.png', 25, 'acessorio'),
(3, 'Lâmpada Básica', 'lampada', 'mothes/assets/deco/lamp1.png', 15, 'decoracao');

-- --------------------------------------------------------

--
-- Table structure for table `cosmetico_compra`
--

CREATE TABLE `cosmetico_compra` (
  `cosmetico_idcosmetico` int NOT NULL,
  `usuario_idUsuario` int NOT NULL,
  `equipado` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cosmetico_compra`
--

INSERT INTO `cosmetico_compra` (`cosmetico_idcosmetico`, `usuario_idUsuario`, `equipado`) VALUES
(1, 1, 0),
(3, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `estudo`
--

CREATE TABLE `estudo` (
  `idEstudo` int NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `ciclos` tinyint DEFAULT NULL,
  `tempoEstudo` int DEFAULT NULL,
  `tempoDescanso` int DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `estudo`
--

INSERT INTO `estudo` (`idEstudo`, `nome`, `ciclos`, `tempoEstudo`, `tempoDescanso`, `idUsuario`) VALUES
(2, 'Teste Atualizado', 4, 10, 2, 1),
(5, 'Default', 4, 1500, 300, 1),
(6, 'Probabilidade', 4, 1500, 300, 1);

-- --------------------------------------------------------

--
-- Table structure for table `mariposa`
--

CREATE TABLE `mariposa` (
  `idMariposa` int NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `estagio` int DEFAULT NULL,
  `qntNectarReal` double DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mariposa`
--

INSERT INTO `mariposa` (`idMariposa`, `nome`, `estagio`, `qntNectarReal`, `idUsuario`) VALUES
(1, 'Mothes', 2, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `recompensa`
--

CREATE TABLE `recompensa` (
  `idrecompensaCert` int NOT NULL,
  `tipoRecompensa` varchar(45) DEFAULT NULL,
  `tipoBonus` varchar(45) DEFAULT NULL,
  `quantidade` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL,
  `apelido` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senhaHash` varchar(255) DEFAULT NULL,
  `qntMoeda` int DEFAULT NULL,
  `salt` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `apelido`, `email`, `senhaHash`, `qntMoeda`, `salt`) VALUES
(1, 'Deky Harvey', 'dekyHarvey@gmail.com', 'hZY54qMZTLhJ9kneZzGYpRjq5AkRfbhNZ62FvjiVS30=', 0, 'fv90Nrkdu+dnG+boKenrlQ==');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `certificado`
--
ALTER TABLE `certificado`
  ADD PRIMARY KEY (`idCertificado`),
  ADD KEY `usuario_idUsuario` (`usuario_idUsuario`);

--
-- Indexes for table `certificado_recompensa`
--
ALTER TABLE `certificado_recompensa`
  ADD PRIMARY KEY (`certificado_idCertificado`,`recompensaCert_idrecompensaCert`),
  ADD KEY `recompensaCert_idrecompensaCert` (`recompensaCert_idrecompensaCert`);

--
-- Indexes for table `cosmetico`
--
ALTER TABLE `cosmetico`
  ADD PRIMARY KEY (`idcosmetico`);

--
-- Indexes for table `cosmetico_compra`
--
ALTER TABLE `cosmetico_compra`
  ADD PRIMARY KEY (`cosmetico_idcosmetico`,`usuario_idUsuario`),
  ADD KEY `usuario_idUsuario` (`usuario_idUsuario`);

--
-- Indexes for table `estudo`
--
ALTER TABLE `estudo`
  ADD PRIMARY KEY (`idEstudo`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indexes for table `mariposa`
--
ALTER TABLE `mariposa`
  ADD PRIMARY KEY (`idMariposa`),
  ADD KEY `usuario_idUsuario` (`idUsuario`);

--
-- Indexes for table `recompensa`
--
ALTER TABLE `recompensa`
  ADD PRIMARY KEY (`idrecompensaCert`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `certificado`
--
ALTER TABLE `certificado`
  MODIFY `idCertificado` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cosmetico`
--
ALTER TABLE `cosmetico`
  MODIFY `idcosmetico` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `estudo`
--
ALTER TABLE `estudo`
  MODIFY `idEstudo` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `mariposa`
--
ALTER TABLE `mariposa`
  MODIFY `idMariposa` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `recompensa`
--
ALTER TABLE `recompensa`
  MODIFY `idrecompensaCert` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `certificado`
--
ALTER TABLE `certificado`
  ADD CONSTRAINT `certificado_ibfk_1` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuarios` (`idUsuario`);

--
-- Constraints for table `certificado_recompensa`
--
ALTER TABLE `certificado_recompensa`
  ADD CONSTRAINT `certificado_recompensa_ibfk_1` FOREIGN KEY (`certificado_idCertificado`) REFERENCES `certificado` (`idCertificado`),
  ADD CONSTRAINT `certificado_recompensa_ibfk_2` FOREIGN KEY (`recompensaCert_idrecompensaCert`) REFERENCES `recompensa` (`idrecompensaCert`);

--
-- Constraints for table `cosmetico_compra`
--
ALTER TABLE `cosmetico_compra`
  ADD CONSTRAINT `cosmetico_compra_ibfk_1` FOREIGN KEY (`cosmetico_idcosmetico`) REFERENCES `cosmetico` (`idcosmetico`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cosmetico_compra_ibfk_2` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `estudo`
--
ALTER TABLE `estudo`
  ADD CONSTRAINT `estudo_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`);

--
-- Constraints for table `mariposa`
--
ALTER TABLE `mariposa`
  ADD CONSTRAINT `mariposa_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
