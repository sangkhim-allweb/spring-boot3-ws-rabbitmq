# spring-boot3-ws-rabbitmq

## websocket security
    https://stackoverflow.com/questions/45405332/websocket-authentication-and-authorization-in-spring

## rabbitmq
    https://medium.com/@saurabh.singh0829/how-to-create-rabbitmq-cluster-in-docker-aws-linux-4b26a31f90bc

    docker run -p 16379:6379 -d redis:7.0 redis-server --requirepass "p@123456"

    docker network create mynet
    docker network inspect mynet

    note 1
    docker run -d --hostname rabbit1 --name myrabbit1 -p 15672:15672 -p 5672:5672 -p 61613:61613 --network mynet -e RABBITMQ_ERLANG_COOKIE=’rabbitcookie’ rabbitmq:management
    note 2
    docker run -d --hostname rabbit2 --name myrabbit2 -p 5673:5672 --link myrabbit1:rabbit1 --network mynet -e RABBITMQ_ERLANG_COOKIE=’rabbitcookie’ rabbitmq:management
    node 3
    docker run -d --hostname rabbit3 --name myrabbit3 -p 5674:5672 --link myrabbit1:rabbit1 --link myrabbit2:rabbit2 --network mynet -e RABBITMQ_ERLANG_COOKIE=’rabbitcookie’ rabbitmq:management

    node 1
    docker exec -it myrabbit1 bash
    rabbitmqctl stop_app
    rabbitmqctl reset
    rabbitmqctl start_app
    rabbitmq-plugins enable rabbitmq_stomp
    rabbitmq-plugins enable rabbitmq_mqtt
    exit

    node 2
    docker exec -it myrabbit2 bash
    rabbitmqctl stop_app
    rabbitmqctl reset
    rabbitmqctl join_cluster --ram rabbit@rabbit1
    rabbitmqctl start_app
    rabbitmq-plugins enable rabbitmq_stomp
    rabbitmq-plugins enable rabbitmq_mqtt
    exit

    node 3
    docker exec -it myrabbit3 bash
    rabbitmqctl stop_app
    rabbitmqctl reset
    rabbitmqctl join_cluster --ram rabbit@rabbit1
    rabbitmqctl start_app
    rabbitmq-plugins enable rabbitmq_stomp
    rabbitmq-plugins enable rabbitmq_mqtt
    exit

    rabbitmdctl cluster_status

    http://localhost:15672
    guest/guest