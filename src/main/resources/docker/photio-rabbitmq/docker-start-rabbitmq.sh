docker stop rabbitmq-docker
docker rm rabbitmq-docker
docker run --name rabbitmq-docker \
-d \
-p 4369:4369 \
-p 5672:5672 \
-p 15672:15672 \
--hostname rabbitmq-docker \
-v /home/docker/photio/photio-rabbitmq/data/:/var/lib/rabbitmq/mnesia/rabbit@rabbitmq-docker \
rabbitmq:3.7-management