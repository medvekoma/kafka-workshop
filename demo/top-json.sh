#!/usr/bin/env bash

top | \
    # Filter out header
    sed -n '/PID/,$p'|sed '/PID/d' | \
    # Convert to json
    awk '{printf "%d\t{\"pid\": %d, \"app\": \"%s\", \"mem\": \"%s\"}\n",
        $1, $1, $2, $8}'
