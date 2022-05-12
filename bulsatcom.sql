DROP DATABASE IF EXISTS bulsatcom;
CREATE DATABASE bulsatcom;
USE bulsatcom;

CREATE TABLE admins(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
egn VARCHAR(10) NOT NULL UNIQUE,
address VARCHAR(255) NOT NULL,
telephone VARCHAR(255) NOT NULL,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL
);

CREATE TABLE providers(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL
);

CREATE TABLE contract_provider(
id INT AUTO_INCREMENT PRIMARY KEY,
provider_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (provider_id)
REFERENCES providers(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
firstDate DATE NOT NULL,
lastDate DATE NOT NULL
);

CREATE TABLE channels(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
contract_provider INT NOT NULL,
CONSTRAINT FOREIGN KEY (contract_provider)
REFERENCES contract_provider(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
category VARCHAR(255) NOT NULL,
price DECIMAL(7,2) NOT NULL
);

CREATE TABLE packages(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
minimum_price DECIMAL(7,2) NOT NULL
);

CREATE TABLE package_channel(
package_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (package_id)
REFERENCES packages(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
channel_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (channel_id)
REFERENCES channels(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);


CREATE TABLE customer(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
egn VARCHAR(10) NOT NULL UNIQUE,
address VARCHAR(255) NOT NULL,
telephone VARCHAR(255) NOT NULL
);

CREATE TABLE contract_customer(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
customer_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (customer_id)
REFERENCES customer(id),
price DECIMAL(7,2) NOT NULL,
firstDate DATE NOT NULL,
lastDate DATE NOT NULL,
package_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (package_id)
REFERENCES packages(id)
);

CREATE TABLE payments(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
customer_id INT NOT NULL, 
CONSTRAINT FOREIGN KEY (customer_id)
REFERENCES customer(id),
month VARCHAR(12),
year VARCHAR(256),
dateOfPayment DATETIME
);


INSERT INTO providers
VALUES 
	(NULL, 'NOVA Broadcasting Group'),
    (NULL, 'WarnerMedia');

INSERT INTO contract_provider
VALUES	(NULL, 1, '2022-04-06', '2024-04-06'),
		(NULL, 2, '2022-04-08', '2024-04-08');

INSERT INTO channels(name, contract_provider, category, price)
VALUES	('Diema', 1, 'cinema', 500),
        ('Diema Family', 1, 'entertainment', 450),
        ('Diema Sport HD', 1, 'sport', 999),
		('Diema Sport 2 HD', 1, 'sport', 999),
        ('Diema Sport 3 HD', 1, 'sport', 999),
        ('Trace Sport Stars HD', 1, 'sport', 999),
        ('The Voce', 1, 'music', 450),
        ('Magic TV', 1, 'music', 405),
		('CINEMAX', 2, 'cinema', 1690),
        ('CINEMAX 2', 2, 'cinema', 1690),
        ('NOVA', 1, 'entertainment', 2000),
		('Kino Nova', 1, 'cinema', 1480),
		('Diema Sport', 1, 'sport', 1380),
        ('NOVA Sport', 1, 'sport', 1290);
        
        
INSERT INTO packages
VALUES 	
		(NULL, 'DIEMAXTRA', '7.99'),
        (NULL, 'CINEMAX', '8.99');
        
INSERT INTO package_channel
VALUES 
		(1,1),
		(1,2),
        (1,3),
        (1,4),
        (1,5),
        (2,9),
        (2,10);
        
INSERT INTO customer(name, egn, address, telephone)
VALUES ('Nikolay Georgiev Petrov', '0011223344', 'Sofia, zh.k. Liulyin - bl.6', '0881234567'),
		('Iliya Ivanov Lukov', '9988776655', 'Montana, ul. Sofronii Vratchanski 15', '0988354759');

INSERT INTO contract_customer(customer_id, price, firstDate, lastDate, package_id)
VALUES (1, '7.99', '2022-03-02', '2024-03-02', 1),
		(2, '8.99', '2022-03-10', '2023-03-14', 2);

INSERT INTO payments(customer_id, month, year, dateOfPayment)
VALUES('1', '03', '2022', '2022-04-05 14:58:25'),
('1', '04', '2022', '2022-05-07 11:42:56'),
('1', '05', '2022', NULL),
('2', '03', '2022', '2022-04-15 10:38:17'),
('2', '04', '2022', '2022-05-11 16:29:38'),
('2', '05', '2022', NULL);

INSERT INTO admins(name, egn, address, telephone, username, password)
VALUES('Rumen Nikolov', '0148201909', 'Vratsa, Spas Sokolov 54' , '0988747626', 'runikolov01', 'ru123');