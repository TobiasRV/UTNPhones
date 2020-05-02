create database utnphones;
use utnphones;

CREATE TABLE `Roles` (
  `id_role` int,
  `rol_name` varchar(50),
  PRIMARY KEY (`id_role`)
);

CREATE TABLE `Cities` (
  `id_city` int,
  `city_name` varchar(50),
  `province` int,
  PRIMARY KEY (`id_city`),
  KEY `FK` (`province`)
);

CREATE TABLE `Provinces` (
  `id_province` int,
  `province_name` varchar(50),
  PRIMARY KEY (`id_province`)
);

CREATE TABLE `Users` (
  `id_user` int,
  `name` varchar(50),
  `lastname` varchar(50),
  `dni` int,
  `id_city` int,
  `id_role` int,
  PRIMARY KEY (`id_user`),
  KEY `UNQ` (`dni`),
  KEY `FK` (`id_city`, `id_role`)
);

CREATE TABLE `Lines` (
  `id_line` int,
  `id_user` int,
  `id_prefix` int,
  `phone_number` int,
  `line_type` int,
  `id_status` int,
  PRIMARY KEY (`id_line`),
  KEY `FK` (`id_user`, `id_prefix`, `line_type`, `id_status`),
  KEY `UNQ` (`phone_number`)
);

CREATE TABLE `Prefixes` (
  `id_prefix` int,
  `id_city` int,
  `prefix` int,
  PRIMARY KEY (`id_prefix`),
  KEY `FK` (`id_city`),
  KEY `UNQ` (`prefix`)
);

CREATE TABLE `Line_types` (
  `id_line_types` int,
  `name` varchar(50),
  PRIMARY KEY (`id_line_types`),
  KEY `UNQ` (`name`)
);

CREATE TABLE `Calls` (
  `id_call` int,
  `id_origin_line` int,
  `id_destination_line` int,
  `call_date` date,
  `id_rate` int,
  `call_duration` int,
  PRIMARY KEY (`id_call`),
  KEY `FK` (`id_origin_line`, `id_destination_line`, `id_rate`)
);

CREATE TABLE `Rates` (
  `id_rate` int,
  `id_origin_prefix` int,
  `id_destination_prefix` int,
  `cost_per_minute` double,
  PRIMARY KEY (`id_rate`),
  KEY `FK` (`id_origin_prefix`, `id_destination_prefix`)
);

CREATE TABLE `Bills` (
  `id_bill` int,
  `id_line` int,
  `production_cost` double,
  `total` double,
  `issue_date` date,
  `expiration_date` date,
  `paid` boolean,
  PRIMARY KEY (`id_bill`),
  KEY `FK` (`id_line`)
);

CREATE TABLE `Statuses` (
  `id_status` int,
  `status_name` varchar(50),
  PRIMARY KEY (`id_status`),
  KEY `UNQ` (`status_name`)
);

