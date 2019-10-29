#!/usr/bin/env sh

curl -XDELETE http://kafka-connect-cp:18083/connectors/my-activemq-source-connector

curl -i -X POST -H "Accept:application/json" \
    -H  "Content-Type:application/json" http://kafka-connect-cp:18083/connectors/ \
    -d '{
      "name": "my-activemq-source-connector",
      "config": {
          "connector.class": "io.confluent.connect.activemq.ActiveMQSourceConnector",
          "kafka.topic":"activemq-foobar",
          "activemq.url":"tcp://activemq:61616",
          "activemq.username":"amq",
          "activemq.password":"amq",
          "jms.destination.name":"foo.bar",
          "jms.destination.type": "queue",
          "confluent.license":"",
          "confluent.topic.bootstrap.servers":"kafka:29092",
          "confluent.topic.replication.factor": "1",
          "errors.tolerance": "all",
          "errors.retry.timeout": "600000",
          "errors.retry.delay.max.ms": "30000",
          "errors.log.enable": "true",
          "errors.log.include.messages": "false"
       }
    }'
