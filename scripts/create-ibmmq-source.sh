#!usr/bin/env sh

curl -i -X POST -H "Accept:application/json" \
    -H  "Content-Type:application/json" http://kafka-connect-cp:18083/connectors/ \
    -d '{
               "name": "ibm-mq-source",
               "config": {
                    "connector.class": "io.confluent.connect.ibm.mq.IbmMQSourceConnector",
                    "kafka.topic": "MyKafkaTopicName",
                    "mq.hostname": "ibmmq",
                    "mq.port": "1414",
                    "mq.transport.type": "client",
                    "mq.queue.manager": "QM1",
                    "mq.channel": "DEV.APP.SVRCONN",
                    "mq.username": "app",
                    "mq.password": "passw0rd",
                    "jms.destination.name": "DEV.QUEUE.1",
                    "jms.destination.type": "queue",
                    "confluent.license": "",
                    "confluent.topic.bootstrap.servers": "kafka:9092",
                    "confluent.topic.replication.factor": "1"
          }}'
