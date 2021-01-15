-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: mirecarga_innodb_consolidationstage
-- ------------------------------------------------------
-- Server version	5.7.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='9109f4a5-d2af-11e9-922c-005056a031ea:1-1486369';

--
-- Table structure for table `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarifas` (
  `id_tarifa` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(500) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `id_pais` bigint(20) DEFAULT NULL,
  `id_departamento` bigint(20) DEFAULT NULL,
  `id_municipio` bigint(20) DEFAULT NULL,
  `tipo_tarifa` int(11) DEFAULT NULL,
  `tipo_vehiculo` int(11) DEFAULT NULL,
  `id_parqueadero` bigint(20) DEFAULT NULL,
  `id_tipo_celda` int(11) DEFAULT NULL,
  `id_celda` bigint(20) DEFAULT NULL,
  `para_incapacitados` bit(1) DEFAULT NULL,
  `para_festivos` bit(1) DEFAULT NULL,
  `id_dia` int(11) DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `id_unidad_de_tiempo` int(11) DEFAULT NULL,
  `precio_minuto` decimal(10,0) DEFAULT NULL,
  `precio` decimal(10,0) DEFAULT NULL,
  `id_convenio` int(11) DEFAULT NULL,
  `precio_convenio` decimal(10,0) DEFAULT NULL,
  `es_prepago` bit(1) DEFAULT NULL,
  `activa` bit(1) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `updated_by` varchar(60) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_tarifa`),
  KEY `fk_tarifa_celda_idx` (`id_celda`),
  KEY `fk_tarifa_pais_idx` (`id_pais`),
  KEY `fk_tarifa_parqueadero_idx` (`id_parqueadero`),
  KEY `fk_tarifa_tipo_vehiculo_idx` (`tipo_vehiculo`),
  KEY `fk_tarifas_convenio_idx` (`id_convenio`),
  KEY `fk_tarifas_departamento_idx` (`id_departamento`),
  KEY `fk_tarifas_dia_idx` (`id_dia`),
  KEY `fk_tarifas_municipio_idx` (`id_municipio`),
  KEY `fk_tarifas_tipo_tarifa_idx` (`tipo_tarifa`),
  KEY `fk_tarifas_unidad_tiempo_idx` (`id_unidad_de_tiempo`),
  KEY `fk_tarifa_tipo_celda_idx` (`id_tipo_celda`),
  CONSTRAINT `fk_tarifa_celda` FOREIGN KEY (`id_celda`) REFERENCES `celdas` (`id_celda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifa_departamento` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id_departamento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifa_pais` FOREIGN KEY (`id_pais`) REFERENCES `pais` (`id_pais`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifa_parqueadero` FOREIGN KEY (`id_parqueadero`) REFERENCES `parqueaderos_y_zonas` (`id_parqueadero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifa_tipo_celda` FOREIGN KEY (`id_tipo_celda`) REFERENCES `tipos_de_celdas` (`id_tipo_celda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifa_tipo_vehiculo` FOREIGN KEY (`tipo_vehiculo`) REFERENCES `tipos_de_vehiculos` (`id_tipo_vehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifas_convenio` FOREIGN KEY (`id_convenio`) REFERENCES `convenios` (`id_convenio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifas_dia` FOREIGN KEY (`id_dia`) REFERENCES `dia` (`id_dia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifas_municipio` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifas_tipo_tarifa` FOREIGN KEY (`tipo_tarifa`) REFERENCES `tipos_de_tarifa` (`id_tipo_tarifa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tarifas_unidad_tiempo` FOREIGN KEY (`id_unidad_de_tiempo`) REFERENCES `unidades_de_tiempo` (`id_unidad_de_tiempo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifas`
--

LOCK TABLES `tarifas` WRITE;
/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` VALUES (1,'Tarifa Plana - Automovil - P1 - Post - Por Minuto - Semanal','Automovil Por Minuto',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,1,60,60,NULL,NULL,_binary '\0',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(2,'Tarifa Plana - Automovil - P1 - Pre - Por 15 Minutos - Semanal','Paq. Automovil 15 Min.',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,50,750,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(3,'Tarifa Plana - Automovil - P1 - Pre - Por 30 Minutos - Semanal','Paq. Automovil 30 Min.',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,3,42,1400,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(4,'Tarifa Plana - Automovil - P1 - Pre - Por 60 Minutos - Semanal','Paq. Automovil 60 Min.',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,4,47,2800,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(5,'Tarifa Plana - Automovil - P1 - Pre - Por 5 Horas - Semanal','Paq. Automovil 5 Horas',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,5,50,15000,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(6,'Tarifa Plana - Automovil - P1 - Pre - Por 1 Día - Semanal','Paq. Automovil 1 Día',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,6,13,18000,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(7,'Tarifa Especial - Automovil - P1 - Pre - Por 15 Minutos - Semanal - H. Esp.','Paq. Automovil (8-9) 15 Min.',1,3,149,1,1,1,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,'08:00:00','09:00:00',2,47,700,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(8,'Tarifa Plana - Automovil - P1 - Pre - Por 15 Minutos - Semanal - Inc','Paq. Automovil (Inc.) 15 Min.',1,3,149,1,1,1,NULL,NULL,_binary '',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,33,500,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(9,'Tarifa Plana - Automovil - P2 - Pre - Por 15 Minutos - Semanal','Paq. Automovil 15 Min.',1,3,149,1,1,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,50,750,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(10,'Tarifa Plana - Moto - P2 - Pre - Por 15 Minutos - Semanal','Paq. Moto 15 Min.',1,3,149,1,3,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,33,500,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(11,'Tarifa Plana - Bicicleta - P2 - Pre - Por 15 Minutos - Semanal','Paq. Bicicleta 15 Min.',1,3,149,1,4,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,20,300,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(12,'Tarifa Plana - Automovil - P2 - Pre - Por 30 Minutos - Semanal','Paq. Automovil 30 Min.',1,3,149,1,1,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,3,42,1400,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(13,'Tarifa Plana - Automovil - P2 - Pre - Por 60 Minutos - Semanal','Paq. Automovil 60 Min.',1,3,149,1,1,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,4,47,2800,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg'),(14,'Tarifa Plana - Automovil - P2 - Pre - Por 15 Minutos - Semanal','Paq. Automovil 15 Min.',2,3,149,1,1,2,NULL,NULL,_binary '\0',_binary '\0',NULL,NULL,NULL,NULL,NULL,2,50,750,NULL,NULL,_binary '',_binary '','2018-01-26 11:46:00','lfg','2018-01-26 11:46:00','lfg');
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-21 15:12:29
