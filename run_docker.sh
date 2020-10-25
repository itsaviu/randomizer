#!/usr/bin/env bash
#docker rm $(docker ps -a -q)
#docker system prune
#docker image prune
#docker container prune

docker-compose -f dev-docker-compose.yml down

docker stop randomizer

docker rmi -f $(docker images |grep 'randomizer')

docker build -f Dockerfile -t randomizer .

echo "starting the service"
docker-compose -f dev-docker-compose.yml -p randomizer up
