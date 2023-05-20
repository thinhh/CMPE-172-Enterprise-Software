package com.example.springcashierworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "starbucks")
public class SpringCashierWorker {

    private static final Logger log = LoggerFactory.getLogger(SpringCashierWorker.class);

    @Autowired
    private OrderRepository orders ;

    @RabbitHandler
    public void processGumballOrders(String orderNumber) {
        log.info( "Received  Order # " + orderNumber) ;

        // Sleeping to simulate buzy work
        try {
            Thread.sleep(20000); // 20 seconds
        } catch (Exception e) {}


        List<Order> list = orders.findByOrderNumber( orderNumber ) ;
        if ( !list.isEmpty() ) {
            Order order = list.get(0) ;
            order.setStatus ( "FULFILLED" ) ;
            orders.save(order) ;
            log.info( "Processed Order # " + orderNumber );
        } else {
            log.info( "[ERROR] Order # " + orderNumber + " Not Found!" );
        } 

    }
}