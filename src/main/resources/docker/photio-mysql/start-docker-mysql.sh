docker stop photio-mysql
docker rm photio-mysql
docker run --name photio-mysql \
-d \
-p 3308:3306 \
-v /home/docker/photio/photio-mysql/init_sql:/docker-entrypoint-initdb.d \
-v /home/docker/photio/photio-mysql/data/log/:/var/log/ \
-v /home/docker/photio/photio-mysql/data/mysqld/:/var/run/mysqld/ \
-v /home/docker/photio/photio-mysql/data/mysql:/var/lib/mysql \
-v /home/docker/photio/photio-mysql/my.cnf:/etc/my.cnf \
mysql/mysql-server:5.7
