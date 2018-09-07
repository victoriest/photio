docker stop zipkin-docker
docker rm zipkin-docker
docker run --name zipkin-docker \
-d \
-p 9411:9411 \
openzipkin/zipkin
