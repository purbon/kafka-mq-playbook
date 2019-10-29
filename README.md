# MQ -> kafka <- playbook

_work in progress_

This repository contains examples and playbooks useful to bring data from MQ systems such as ActiveMQ and RabbitMQ or IBMMQ into Apache Kafka using the Kafka Connect Framework.

# Quickstart guide

1. Run `./up [activemq, ibmmq, rabbitmq]`

### IBMMQ/DB2 playbook

TBA

Connect to DB2 in the container:

* `docker exec -it db2ContainerId /bin/bash`
* `su - db2inst1`
* `db2 connect to testdb user db2inst1 using passw0rd`

### RabbitMQ playbook

TBA

### Active MQ playbook

2. Run `docker-compose exec kafka-connect-cp /scripts/create-activemq-source-with-tolerance-all.sh`
3. Start Kafka console consumer `docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --from-beginning --topic
activemq-foobar`
4. Open ActiveMQ admin interface http://127.0.0.1:8161/admin/send.jsp?JMSDestination=foo.bar&JMSDestinationType=queue (username: admin, password: admin)
5. Send a message
6. Observe a message being produced by the JMS connector to the `activemq-foobar` topic


To shutdown the playbook use:

1. `./down [activemq, ibmmq, rabbitmq]`
