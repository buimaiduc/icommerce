#!/bin/bash
service=${1:-all}

if [ "$service" = "all" ]; then
    echo "Remove kafka"
    docker rm -f kafka_dev
    docker rm -f zookeeper_dev

    echo "Remove MongoDB"
    docker rm -f mongo_dev

    echo "Remove Neo4J"
    docker rm -f neo4j_dev

    echo "Remove MySQL"
    docker rm -f mysql_dev

    echo "Remove Redis"
    docker rm -f redis_dev

    echo "Remove Rabbitmq"
    docker rm -f rabbitmq_dev

    echo "Remove Solr"
    docker rm -f solr_dev
elif [ "$service" = "kafka" ]; then
    echo "Remove $service"
    docker rm -f kafka_dev zookeeper_dev
else 
    echo "Remove $service"    
    docker rm -f "$service"
fi
