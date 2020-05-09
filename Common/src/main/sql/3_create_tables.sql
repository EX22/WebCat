USE `web_cat`;

CREATE TABLE IF NOT EXISTS `administration` (
	`user_id` INT(11) NOT NULL AUTO_INCREMENT,
	`login` VARCHAR(250) NOT NULL,
	`password` VARCHAR(250) NOT NULL,
	`role` TINYINT(4) NOT NULL
	PRIMARY KEY (`user_id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `blacklist` (
	`customer_id` INT(11) NOT NULL,
	`login` INT(11) NOT NULL,
	INDEX `FK__customers` (`customer_id`),
	CONSTRAINT `FK__customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


CREATE TABLE IF NOT EXISTS `cart` (
	`cart_id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_id` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`cart_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `cart_content` (
	`customer_id` INT(11) NOT NULL,
	`product_id` INT(11) NOT NULL,
	`product_count` INT(11) NULL DEFAULT NULL,
	INDEX `FK__products` (`product_id`) USING BTREE,
	INDEX `FK__customer_id` (`customer_id`) USING BTREE,
	PRIMARY KEY (`customer_id`, `product_id`),
	CONSTRAINT `FK__products` FOREIGN KEY (`product_id`) REFERENCES `web_cat`.`products` (`product_id`) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `web_cat`.`customers` (`customer_id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;




CREATE TABLE IF NOT EXISTS `category` (
	`category_id` INT(11) NOT NULL AUTO_INCREMENT,
	`category_name` VARCHAR(255) NOT NULL,
	`category_description` VARCHAR(255) NOT NULL,
	`in_stock` VARCHAR(50) NOT NULL,
	`seo_attributes` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`category_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `customers` (
	`customer_id` INT(11) NOT NULL AUTO_INCREMENT,
	`login` VARCHAR(150) NOT NULL,
	`password` VARCHAR(250) NOT NULL,
	`name` VARCHAR(250) NULL DEFAULT NULL,
	`phone_number` VARCHAR(355) NULL DEFAULT NULL,
	`email` VARCHAR(355) NULL DEFAULT NULL,
	`ip` VARCHAR(150) NULL DEFAULT NULL,
	`location` VARCHAR(250) NULL DEFAULT NULL,
	`customer_status` VARCHAR(150) NULL DEFAULT NULL,
	`discount` DOUBLE NULL DEFAULT NULL,
	`role` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`customer_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `customer_contacts` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_id` INT(11) NULL DEFAULT NULL,
	`last_name` VARCHAR(250) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`shipping_address` VARCHAR(250) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`country` VARCHAR(250) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`state` VARCHAR(250) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`zip_code` VARCHAR(250) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_customer_contacts_customers` (`customer_id`) USING BTREE,
	CONSTRAINT `FK_customer_contacts_customers`
	 FOREIGN KEY (`customer_id`) REFERENCES `web_cat`.`customers` (`customer_id`)
	 ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;



CREATE TABLE IF NOT EXISTS `orders` (
	`order_id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_id` INT(11) NOT NULL,
	`order_price` DOUBLE(22,0) NOT NULL,
	`order_status` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`order_date` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`shipping_address` VARCHAR(250) NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`order_id`) USING BTREE,
	INDEX `FK_orders_customers` (`customer_id`) USING BTREE,
	CONSTRAINT `FK_orders_customers` FOREIGN KEY (`customer_id`) REFERENCES `web_cat`.`customers` (`customer_id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

;



CREATE TABLE IF NOT EXISTS `order_details` (
	`order_id` INT(11) NOT NULL,
	`product_id` INT(11) NOT NULL,
	`product_price` DOUBLE NOT NULL,
	`product_amount` INT(11) NOT NULL,
	`discount` DOUBLE NOT NULL,
	INDEX `Index 1` (`order_id`),
	INDEX `FK_order_details_products` (`product_id`),
	CONSTRAINT `FK_order_details_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
	CONSTRAINT `FK_order_details_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `products` (
	`product_id` INT(11) NOT NULL,
	`category_id` INT(11) NOT NULL,
	`product_name` VARCHAR(150) NOT NULL,
	`product_description` VARCHAR(500) NOT NULL,
	`product_price` DOUBLE NOT NULL,
	`product_discount` DOUBLE NOT NULL,
	`in_stock` VARCHAR(50) NOT NULL,
	`photo_path` VARCHAR(250) NOT NULL,
	`seo_attributes` VARCHAR(250) NOT NULL,
	`output_marker` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`product_id`),
	INDEX `FK_products_category` (`category_id`),
	CONSTRAINT `FK_products_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


CREATE TABLE IF NOT EXISTS `products_category` (
	`product_id` INT(11) NOT NULL,
	`cateory_id` INT(11) NOT NULL,
	INDEX `Index 1` (`product_id`),
	INDEX `Index 2` (`category_id`),
	CONSTRAINT `FK_products_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
	CONSTRAINT `FK_products_category_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `stock` (
	`product_id` INT(11) NOT NULL,
	`amount_in_stock` INT(11) NOT NULL,
	INDEX `Index 1` (`product_id`),
	CONSTRAINT `FK_stock_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
