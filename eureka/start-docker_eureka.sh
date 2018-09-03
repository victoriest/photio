docker stop photio-eureka
docker rm photio-eureka
docker run --name photio-eureka \
-d \
-p 8090:8090 \
-v /home/docker/photio/photio-eureka/:/home/docker/photio/photio-eureka/ \
photio-eureka