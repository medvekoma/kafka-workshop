#!/bin/bash

kafkacat -C -b localhost:9092 -t apps | jq .
