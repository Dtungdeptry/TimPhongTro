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
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `status` enum('pending','approved','rejected') DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price_range_id` int DEFAULT NULL,
  `room_type_id` int DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `area_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `fk_price_range` (`price_range_id`),
  KEY `fk_room_type` (`room_type_id`),
  KEY `fk_location` (`location_id`),
  KEY `fk_area` (`area_id`),
  CONSTRAINT `fk_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `fk_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_price_range` FOREIGN KEY (`price_range_id`) REFERENCES `price_range` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_room_type` FOREIGN KEY (`room_type_id`) REFERENCES `room_type` (`id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (15,28,'A001 Nhà Nguyên Căn','Nhà rộng rãi, tiện nghi.','rejected','2025-03-09 04:47:49','2025-03-11 11:28:53',5,3,3,5),(16,21,'A001 Căn Hộ','New','approved','2025-03-11 09:23:02','2025-03-11 09:23:02',1,2,3,4),(20,29,'A001 Căn hộ','Đầy đủ tiện nghi, view đẹp.','approved','2025-03-11 11:27:53','2025-03-17 16:49:20',3,2,2,2),(22,21,'A002 Căn Hộ','Trang 278','approved','2025-03-11 16:02:57','2025-03-13 10:30:29',3,2,4,2),(27,28,'A002 Chung Cư Mini','Chung cư mini giá rẻ, nhà mặt đường\n','approved','2025-03-12 14:50:16','2025-03-13 10:30:29',4,4,2,2),(28,28,'A003 Nhà Nguyên Căn','Nhà nguyên căn 3 tầng khu vực Đống Đa','approved','2025-03-12 16:03:44','2025-03-13 12:28:46',5,3,5,3),(29,28,'A004 Phòng Trọ','Phòng trọ giá rẻ','approved','2025-03-12 16:10:10','2025-03-13 12:28:46',2,2,3,1),(30,28,'A005 Phòng Trọ','Phòng sạch rộng thoáng','approved','2025-03-13 07:22:17','2025-03-13 12:28:46',4,1,2,2),(31,28,'A006 Căn Hộ ','Căn hộ cao cấp phù hợp gia đình','approved','2025-03-13 12:27:46','2025-03-13 12:28:46',3,2,3,1),(32,28,'A007 Căn Hộ','Căn hộ cao cấp dành cho các gia đình có con nhỏ\n','approved','2025-03-13 12:28:24','2025-03-13 12:28:46',5,2,4,4),(33,28,'A008 Phòng Trọ','ádasdasd','approved','2025-03-13 12:33:25','2025-03-13 13:04:50',1,2,5,1),(34,28,'A009 Chung Cư Mini','ádasd','approved','2025-03-13 12:33:31','2025-03-13 13:04:50',2,2,7,4),(35,28,'A010 Nhà Nguyên Căn','ádasdaaaaaaaaaaa','approved','2025-03-13 12:33:41','2025-03-13 13:04:50',5,3,3,1),(36,28,'A011 Chung cư Mini','aaaaaaaaaaaaaaaa','approved','2025-03-13 12:33:49','2025-03-13 13:04:50',2,3,3,1),(37,28,'A012 Nhà Nguyên Căn','sss','pending','2025-03-17 10:48:43',NULL,2,3,3,2);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
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
