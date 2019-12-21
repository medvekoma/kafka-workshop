#!/usr/bin/env bash

TOPIC_NAME=${1-apps}
PARTITIONS=${2-2}
REPLICATION_FACTOR=${3-2}

kafka-topics --bootstrap-server localhost:9092 --create \
    --topic ${TOPIC_NAME} \
    --partitions ${PARTITIONS} \
    --replication-factor ${REPLICATION_FACTOR}
