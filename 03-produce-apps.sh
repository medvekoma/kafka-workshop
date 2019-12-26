#!/bin/bash

top-json.sh | kafkacat -P -b localhost:9092 -t apps -K '\t'
