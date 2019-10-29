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
          "transforms": "InsertSourceDetails,FlagMessagesWithErrors,SelectorRouter",
          "transforms.InsertSourceDetails.type":"org.apache.kafka.connect.transforms.InsertField$Value",
          "transforms.InsertSourceDetails.static.field":"messagesource",
          "transforms.InsertSourceDetails.static.value":"ActiveMQ",
          "transforms.FlagMessagesWithErrors.type" : "com.purbon.kafka.connect.transformations.FlagMessagesWithErrors",
          "transforms.FlagMessagesWithErrors.message.header.error.field" : "error.field",
          "transforms.SelectorRouter.type": "com.purbon.kafka.connect.transformations.SelectorRouter",
          "transforms.SelectorRouter.message.header.error.field" : "error.field",
          "transforms.SelectorRouter.message.connector.source.dlq" : "SelectorRouterTest.DLQ",
          "errors.log.enable": "true"
       }
    }'
