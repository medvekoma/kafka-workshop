#!/usr/bin/env bash

TOPIC_NAME=${1-apps}

kafka-topics --bootstrap-server localhost:9092 --create \
    --topic ${TOPIC_NAME} \
    --partitions 2 \
    --replication-factor 2
