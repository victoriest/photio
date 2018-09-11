docker stop photio-eureka
docker rm photio-eureka
docker run --name photio-eureka \
-d \
-p 8090:8090 \
-v /home/docker/eureka/:/home/docker/eureka/ \
-v /home/docker/eureka/bootstrap.yml:/home/docker/eureka/bootstrap.yml \
-v /home/docker/eureka/application.yml:/home/docker/eureka/application.yml \
photio-eureka java -jar /home/docker/eureka/eureka-0.1.jar
