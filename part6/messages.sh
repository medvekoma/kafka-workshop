#!/usr/bin/env bash

codes=(
    "HU"
    "EN"
    "DE"
    "RU"
    "PL"
    "RO"
)
size=${#codes[@]}

while true; do
    index=$(($RANDOM % $size))
    echo -e "{\"time\": \"$(date +%T)\", \"code\": \"${codes[$index]}\"}"
    sleep 0.2
done
