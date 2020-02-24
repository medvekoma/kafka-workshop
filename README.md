# Kafka Workshop

This repo contains the examples and materials of the kafka worshop.

Lectures to watch at home will be taken from the Linkedin Learning course
[Apache Kafka for Beginners](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners).

## Environment

You can easily set up a dockerized test environment using the utilities
from the `cluster` folder.

```bash
# Create cluster
./cluster-up.sh

# Delete cluster
./cluster-down.sh
``` 

If you don't have the official kafka tooling installed, you can use the commands from the
`cluster` folder. I recommend to add this folder to your path, so that the commands are available
from all subfolders.

