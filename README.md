# stack
1. spring data jpa
2. flywaydb
3. spring security
4. spring boot
5. mysql
6. docker

# starting the project
1. docker run --detach --env MYSQL_ROOT_PASSWORD=password12 --env MYSQL_USER=mysql-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=ecommdb --name mysql --publish 3306:3306 mysql:8.0

2. docker container run -p 8080:8080 --link=mysql -e RDS_HOSTNAME=mysql  ecomm/ecommerce:0.0.1-SNAPSHOT

