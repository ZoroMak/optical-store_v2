CREATE DATABASE  IF NOT EXISTS `javaschema` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `javaschema`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: javaschema
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `tel` varchar(45) NOT NULL,
  `address` varchar(255) NOT NULL,
  `additional_address` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `promocode` varchar(45) DEFAULT NULL,
  `total_price` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `total_price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_idx` (`user_email`),
  CONSTRAINT `customer` FOREIGN KEY (`user_email`) REFERENCES `customers` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_session`
--

DROP TABLE IF EXISTS `persistent_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `series` varchar(64) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `last_used` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_session`
--

LOCK TABLES `persistent_session` WRITE;
/*!40000 ALTER TABLE `persistent_session` DISABLE KEYS */;
INSERT INTO `persistent_session` VALUES (1,'0A2D077AD5668CB562DBED442D116B0E','zorov.ma@mail.ru',NULL,'2024-03-20 17:28:17'),(2,'23DE1E9E7DAEB0A1064AE1371023D2F9','zorov.ma@mail.ru',NULL,'2024-03-20 17:36:42'),(3,'7AD19DA656BC6F3B26A277D62B113F30','zorov.ma@mail.ru',NULL,'2024-03-20 18:09:04'),(4,'84A449DF4085F1975DC0221098920B5C','rfr@daerfw.wd',NULL,'2024-03-21 13:10:56');
/*!40000 ALTER TABLE `persistent_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `dataArt` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `cost` int NOT NULL,
  `image` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `productcol` int NOT NULL,
  PRIMARY KEY (`dataArt`),
  FULLTEXT KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Оправа Vogue VO5161',10999,'/img/Оправа1.png','/home',10),(2,'Оправа Ray-Ban Aviator RB3025',10500,'/img/оправа2.png','/home',15),(3,'Оправа Oakley OX5132',17800,'/img/оправа3.png','/home',13),(4,'Оправа Prada PR 15VS',32400,'/img/оправа4.png','/home',9),(5,'Оправа Gucci GG0061S',23250,'/img/оправа5.png','/home',21),(6,'Поляризованные линзы',15500,'/img/линзы1.png','/home',17),(7,'Линзы с защитой от ультрафиолета',26900,'/img/линзы2.png','/home',7),(8,'Прозрачные линзы',33250,'/img/линзы3.png','/home',24),(9,'Лунное Орбитальное',20700,'/img/линзы4.png','/home',2);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `authority` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'USER'),(2,'ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `birthday` date NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'ROLE_USER',
  `support_id` varchar(100) DEFAULT NULL,
  `tockenJWT` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Тест','Тестов','admin@mail.ru','$2a$10$TgunL7ZL/Gr22ADCZ5sD.Ow1dp/Ekfx9dmm14Yj/eDfajjRMrImQK','2004-04-04','ROLE_ADMIN',NULL,'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW5AbWFpbC5ydSIsImV4cCI6MTczMDE5NzYzMSwicm9sZXMiOiJBRE1JTiJ9.FXwx2s4EbvKTNHJ3xgk8zJ6a_Tyqrfu0Ea7h4sIc1Zuz7lDhB-g5Blcoczk5Trsc1RxU-1S0_j7-TOCAre-l1-iDqPLiPn29iKCbe231-5rLS-Nr00F05hq_1K2oRt_foD_jp7UcUdXbKlcnQdWXpa_8qbUuX2J0zkJQi9ZgHZs8lf80sJMazpBJIuzqfl5cbsrqjqX9-S15pH1uyJ80vlhvI5t8XfimARJFa81fOb4Pi7ym_fAjQjE05Ad0nTVTgCxpZ7ixDoGKZsnhaE8IAi9HDRHe6eQSB7_CWGAllmO1iTQeRipHFNUOKpxA63kO8f9oNex4YWOGibDm08p0Ag'),(4,'Тест','Пользователь','test1234@mail.ru','$2a$10$vMfZsgDeooGW6d.pA9.mXOK9Uoho0jJrnvQjkhNSplaXgazfBWD02','2004-04-04','ROLE_USER','admin@mail.ru','eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidGVzdDEyMzRAbWFpbC5ydSIsImV4cCI6MTczMDIwNzU4NSwicm9sZXMiOiJVU0VSIn0.C1zoFZnPR7B4EYX_JusHlDPQL6R_zYd1PPH6zp-t1JBRgT020GWBZMQ6B-39ANzH12gRbtoOZtz-JQ5nRzTSaUL-RGE_MixWtoln1JaZs3jtBwkI9AiqC_xAr4CcXjhzlBrujj3q2cigZaqtwlVqIzHeTD1RgmwOWmONFlfNaDlw9qkXT5MKl530_UtGP-pMTFibjxZtk_qxWhDDYGTBNMqeO9QO-A5t3qx_C0A1sCioTCAuzLCFK0g-6qYNvcKtoQFAr2c_xgJkUt_8TBm7b1Eu4GoW5MURo6wiwiBGMsrwp0i-Ul_dofVh5nydrDjkJ-cr0Wz_SsBE8i3VcLobuA');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (3,1,2),(5,4,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-30 13:05:47
