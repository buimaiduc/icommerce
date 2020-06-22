## Overview

Contains common docker images for MBC.

## Getting started
### Service names
1. solr
2. mongodb
3. zookeeper
4. kafka
5. neo4j
6. redis
7. solr
8. mongo_connector

### Start & stop service
Execute 
./start.sh [service_name]

./stop.sh [service_name]

### Kafka
In some cases your machine might be configured to have more than 1 ipv4. (eth0, bridge, etc.)
If so please use the following command to start kafka:

./start.sh kafka <YOUR_IP> without the bracket.

The ip should be your main network interface, NIC connected to the network (IE. eth0).