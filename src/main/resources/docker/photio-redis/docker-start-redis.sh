docker stop photio-redis
docker rm photio-redis
docker run --name photio-redis \
-d \
-p 7002:7000 \
-v /home/docker/photio/photio-redis/data/:/data \
-v /home/docker/photio/photio-redis/data/:/home/redis/ \
-v /home/docker/photio/photio-redis/redis.conf:/home/docker/photio/photio-redis/redis.conf \
redis:3.2 redis-server /home/docker/photio/photio-redis/redis.conf
