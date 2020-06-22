#!/bin/bash

service=${1:-all}

if [ "$service" = "all" ]; then
    echo "Stop MongoDB"
    docker stop mongo_dev

    echo "Stop MySQL"
    docker stop mysql_dev

    echo "Stop Redis"
    docker stop redis_dev

    echo "Stop Rabbitmq"
    docker stop rabbitmq_dev
else
    echo "Stop $service"
    docker stop "${service}_dev"
fi

