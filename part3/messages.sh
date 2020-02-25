#!/usr/bin/env bash

lines=(
    "Apache Kafka is an open-source"
    "stream-processing software platform"
    "developed by LinkedIn"
    "and donated to the Apache Software Foundation,"
    "written in Scala and Java."
    "The project aims to provide a unified,"
    "high-throughput, low-latency platform"
    "for handling real-time data feeds."
    "Kafka can connect to external systems"
    "(for data import/export)"
    "via Kafka Connect"
    "and provides Kafka Streams,"
    "a Java stream processing library."
)
size=${#lines[@]}

while true; do
    index=$(($RANDOM % $size))
    echo "{\"rand\": $(( 10 + RANDOM % 89)), \"time\": \"$(date +%T)\", \"text\": \"${lines[$index]}\"}"
    sleep 0.2
done
