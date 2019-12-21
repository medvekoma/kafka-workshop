#!/usr/bin/env bash

TOPIC_NAME=${1-apps}

kafka-topics --bootstrap-server localhost:9092 --delete \
    --topic ${TOPIC_NAME}
