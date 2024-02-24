# create databases
CREATE DATABASE IF NOT EXISTS `keycloak`;

CREATE DATABASE IF NOT EXISTS `auth`;

CREATE DATABASE IF NOT EXISTS `notification`;

CREATE DATABASE IF NOT EXISTS `test`;

# create root user and grant rights
##CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
##GRANT ALL ON *.* TO 'root'@'%';