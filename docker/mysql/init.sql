-- 创建数据库
CREATE DATABASE IF NOT EXISTS nacos;
CREATE DATABASE IF NOT EXISTS user_service_db;

-- 创建用户并为其设置密码
CREATE USER IF NOT EXISTS 'nacos'@'%' IDENTIFIED BY '0d000721';
CREATE USER IF NOT EXISTS 'user_service_user'@'%' IDENTIFIED BY '0d000721';

-- 授予该用户对数据库的权限
GRANT ALL PRIVILEGES ON nacos.* TO 'nacos'@'%';
GRANT ALL PRIVILEGES ON user_service_db.* TO 'user_service_user'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
