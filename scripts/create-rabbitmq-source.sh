#!/usr/bin/env sh

curl -i -X POST -H "Accept:application/json" \
    -H  "Content-Type:application/json" http://kafka-connect-cp:18083/connectors/ \
    -d '{
      "name": "rabbitmq-source-connector",
      "config": {
         "name" : "rabbitmq-source-connector",
         "connector.class" : "io.confluent.connect.rabbitmq.RabbitMQSourceConnector",
         "tasks.max" : "1",
         "kafka.topic" : "foo.bar",
         "rabbitmq.queue" : "queue.foo.bar"
       }
    }'
