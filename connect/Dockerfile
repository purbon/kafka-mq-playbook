FROM confluentinc/cp-kafka-connect:5.2.1
MAINTAINER pere.urbon@acm.org
ENV container docker

# 1. install kafka connect rabbitmq
RUN confluent-hub install confluentinc/kafka-connect-rabbitmq:1.0.0-preview --no-prompt
