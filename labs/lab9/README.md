# CMPE 172 - Lab #9 Notes
## Spring-RabbitMQ
![1](./spring-rabbitmq/images/rabbit-1.png)

## Spring-RabbitMQ-HelloWorld
![2](./spring-rabbitmq/images/rabbit-2.png)
![3](./spring-rabbitmq/images/rabbit-3.png)
![4](./spring-rabbitmq/images/rabbit-4.png)
![5](./spring-rabbitmq/images/rabbit-5.png)
![6](./spring-rabbitmq/images/rabbit-6.png)
## Spring-RabbitMQ-Workers
![7](./spring-rabbitmq/images/rabbit-7.png)
![8](./spring-rabbitmq/images/rabbit-8.png)
![9](./spring-rabbitmq/images/rabbit-9.png)
![10](./spring-rabbitmq/images/rabbit-10.png)

## Discussion
- A discussion of what Spring Profiles are and how they can be used in your Project <br>
    Spring Profiles are a core feature of the framework — allowing us to map our beans to different profiles — for example, dev, test, and prod. Spring Profiles can be use to active @Bean that only be use in development but not for production. We can use profiles to hide what we don't want other people to see for our production <br>

- A discussion of how RabbitMQ can be used in your Project (i.e. what's the use case?) <br>
    RabbitMQ can be use to send orders from the client side to the backend cashier for processing the payment. 
    Multiple orders can be placed on a queue while waiting for the backend to process each order. 