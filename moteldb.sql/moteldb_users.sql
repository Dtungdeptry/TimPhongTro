-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: moteldb
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` text,
  `role_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `otp` varchar(6) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2a$10$llttPBTsI2fh6MQa8ZU2XeiX5uDsloUZ0siuxWEVhUdzeT1d9IyZG','admin@example.com','0123456789','Admin User 1','Hà Nội',1,'2025-02-28 12:39:42','17000',1),(17,'tung5689','$2a$10$DTGmYTM1FhmAFg/71BglkOANE9rNs4i3tMnKKzxF2yq0zItEUfYQu','tung5689@gmail.com','095845384','Tùng 5689','Hà Nội',3,'2025-03-01 07:29:06',NULL,0),(21,'trang278','$2a$10$7XskzaRdQpppadDsEV0aFO.y5z2WKZ8QKcdBWMZypemhpxXn3tbbi','trang278@gmail.com','0123434889','Trang 278','Hà Nội',3,'2025-03-01 07:57:21',NULL,0),(28,'dtung6898','$2a$10$lRmNjpJFPvPhvc1c.4AZHOYthJduOjGELIPRVT81Yuq2ar0Vj3iTy','dtung6898@gmail.com','0586540370','Dương Xuân Hoàng Tùng','Hà Đông',3,'2025-03-09 04:34:53',NULL,0),(29,'dtung123','$2a$10$aEeR/1xlrFNnWq1sMgXVjOpWf4SkJWF21GMKFraAFeJ1X4INoaDAC','dtung123@gmail.com','0283942933','Dương Tùng','Vinh',3,'2025-03-11 05:31:06',NULL,0),(30,'trang2712','$2a$10$8SG56/IrW3W9Dx18H/hgTeAAIO.3M54WIMaB14REXjGIcZqf6Q8Ni','trang2712@gmail.com','0438293944','Vũ Thùy Trang','Quảng Ninh',2,'2025-03-11 06:01:42',NULL,0),(31,'dtung444','$2a$10$HFGxgHuWKeiWHZvoxSIrFOGZ5ksdU4ch.0gpom3HyTi2wBqqtQOoy','dtung444@gmail.com','0488599322','Dương Xuân Hoàng Tùng','Hà Nội',3,'2025-03-11 11:40:07',NULL,0),(32,'dtung555','$2a$10$zsCq2t2EfzlUxpyE/EE8ueTAhmcXwN//OnHIXbkl0kfpn7oJ/7zOy','dtung555@gmail.com','0485939222','Dương Hoàng Tùng','Hà Nội',2,'2025-03-12 16:17:14',NULL,0),(33,'dxhtung123','$2a$10$S5cJcNyg67KMovoCGtH85e.waiGbPKgxHmEg66Yo9jB2/vBsKo3uG','dxhtung123@gmail.com','0485933848','Nguyễn Thanh Tùng','Vinh',2,'2025-03-14 15:08:42',NULL,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-19  0:31:55
