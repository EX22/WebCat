CREATE DATABASE IF NOT EXISTS `web_cat` DEFAULT CHARACTER SET utf8;

CREATE USER 'web_cat_user'@'%' IDENTIFIED BY 'web_cat_password';

CREATE USER 'web_cat_user'@'localhost' IDENTIFIED BY 'web_cat_password';

GRANT ALL ON web_cat.* TO 'web_cat_user'@'localhost';

GRANT ALL ON web_cat.* TO 'web_cat_user'@'%';