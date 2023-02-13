DROP TABLE IF EXISTS `Location`;

CREATE TABLE `Location` (
  `id` bigint NOT NULL,
  `postcode` varchar(8) NOT NULL,
  `latitude` decimal(10,7) NULL,
  `longitude` decimal(10,7) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4;
