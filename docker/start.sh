#!/bin/bash

service=${1:-all}
custom_kafka_ip=${2}
ip_from_ipconfig=$(ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')

export HOST_IP="${custom_kafka_ip:-$ip_from_ipconfig}"
echo "Host IP ${HOST_IP}"

if [ "$service" = "all" ]; then
    echo "Starting all service..."
    docker-compose up -d
else
    echo "Starting $service"
    docker-compose up -d "$service"    
fi