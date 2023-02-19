CREATE TABLE users(
	user_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	password varchar(300) NOT NULL,
	address varchar(150) NOT NULL,
	role tinyint NOT NULL,
	is_active tinyint NOT NULL,
	is_admin tinyint NOT NULL,
	is_not_locked tinyint NOT NULL,
	is_verified tinyint NOT NULL,
	date_created DATETIME,
	last_updated DATETIME,
	PRIMARY KEY (user_id)
);

CREATE TABLE roles(
	role_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(200) NOT NULL,
	created_at DATETIME,
	updated_at DATETIME,
	PRIMARY KEY (role_id)
);

CREATE TABLE user_roles(
 	user_roles_id bigint NOT NULL AUTO_INCREMENT,
	user_id bigint NOT NULL,
	role_id bigint NOT NULL,
	PRIMARY KEY (user_roles_id),
	FOREIGN KEY (role_id) references roles(role_id),
	FOREIGN KEY (user_id) references users(user_id)
);

CREATE TABLE category(
	category_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE,
	slug varchar(100) NOT NULL,
	date_created DATETIME,
	last_updated DATETIME,
	PRIMARY KEY (category_id)
);

CREATE TABLE product(
	product_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE,
	slug varchar(100) NOT NULL,
	description varchar(2000) NOT NULL,
	price double NOT NULL,
	category_id bigint NOT NULL,
	quantity bigint NOT NULL,
	sold bigint NOT NULL,
	photo varchar(2000) NOT NULL,
	shipping tinyint NOT NULL,
	date_created DATETIME,
	last_updated DATETIME,
	PRIMARY KEY (product_id),
	FOREIGN KEY (category_id) references category(category_id)
);


INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', NOW(), NOW());
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', NOW(), NOW());


