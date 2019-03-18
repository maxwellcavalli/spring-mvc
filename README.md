# spring-mvc

Este projeto foi criado para fins de estudo.

Pre-Requisitos:
- Maven
- Java 1.8
- MySQL 5.7
- Eclipe / IntellyJ


Passos para rodar o projeto

git clone https://github.com/maxwellcavalli/spring-mvc.git


- MYSQL
sudo su <enter>

mysql -u root -p <enter>
<colocar a senha e enter>

CREATE DATABASE orders; <enter>

<sair do mysql>

cd order-example/database


mysql -u root -p orders < Dump20190317.sql



-JAVA

cd order-example

mvn clean install

mvn tomcat7:run-war

