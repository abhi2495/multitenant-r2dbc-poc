CREATE TABLE `employee` (
  `id` SERIAL,
  `name` VARCHAR(45) NOT NULL,
  `age` VARCHAR(45) NOT NULL,
  `reports_to` BIGINT UNSIGNED NULL,
  `grade` VARCHAR(3),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`reports_to`) REFERENCES `employee`(`id`),
  FOREIGN KEY (`grade`) REFERENCES `grade`(`id`)
  );