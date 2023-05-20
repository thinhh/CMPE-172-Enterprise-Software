package com.example.springcashier;

import com.example.springcashier.model.Card;
import com.example.springcashier.model.Ping;
import com.example.springcashier.model.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Controller
@RequestMapping("/user/starbucks")
public class SpringCashierController {

    @Value("${starbucks.client.apikey}") String API_KEY ;
    @Value("${starbucks.client.apihost}") String API_HOST ;
    @Value("${starbucks.client.register}") String REGISTER ;
     
    @Autowired
    private RabbitTemplate rabbit;

    @Autowired
    private Queue queue;    

    @Autowired
    private OrderRepository orders;

    @GetMapping
    public String getAction( @ModelAttribute("command") Command command, 
                            Model model, HttpSession session) {

        String message = "" ;

        command.setRegister( "5012349" ) ;
        message = "Starbucks Reserved Order" + "\n\n" +
            "Register: " + command.getRegister() + "\n" +
            "Status:   " + "Ready for New Order"+ "\n" ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;

    }
    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = "" ;
        String message = "";
        log.info( "Action: " + action ) ;
        command.setRegister( command.getStores() ) ;
        log.info( "Command: " + command ) ;
        
        // Set API Key Header
        headers.set( "apikey", API_KEY ) ;

         if (action.equals("PING")) {
            message = "PING";
            resourceUrl = "http://"+API_HOST+"/ping?apikey="+API_KEY;
            System.out.println(resourceUrl);
            // get response as string
            ResponseEntity<String> stringResponse = restTemplate.getForEntity(resourceUrl, String.class, API_KEY );
            message = stringResponse.getBody();
            // get response as POJO
            ResponseEntity<Ping> pingResponse = restTemplate.getForEntity(resourceUrl, Ping.class, API_KEY);
            Ping pingMsg = pingResponse.getBody();
            System.out.println( pingMsg );
            // pretty print JSON
            try {
                message = "Starbucks Reserved Order" + "\n\n" +
                "test: " + pingMsg.getTest() + "\n";
            }
            catch ( Exception e ) {}
        }

        if (action.equals("NEW CARD")) {
            message = "";
            resourceUrl = "http://"+API_HOST+"/cards?apikey="+API_KEY;
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> newCardRequest = new HttpEntity<String>(emptyRequest, headers) ;
            ResponseEntity<Card> newCardResponse = restTemplate.postForEntity(resourceUrl, newCardRequest, Card.class);
            Card newCard = newCardResponse.getBody();
            System.out.println( newCard );
            // pretty print JSON
            try {
                message = "\n" + "Starbucks Reserved Order" + "\n\n" +
                "Card Number: " + newCard.getCardNumber() + "\n" +
                "Card Code: " + newCard.getCardCode() + "\n" +
                "Balance: " + newCard.getBalance() + "\n" +
                "Activated: " + newCard.isActivated() + "\n" +
                "Status: " + newCard.getStatus() + "\n";

            }
            catch ( Exception e ) {}
        }

        /* Process Post Action */
        if ( action.equals("PLACE ORDER") ) {
            resourceUrl = "http://"+API_HOST+"/order/register/"+command.getRegister()+"?apikey="+API_KEY;
            Order orderRequest = new Order();
            orderRequest.setRegister(command.getRegister());
            orderRequest.setDrink(command.getDrink());
            orderRequest.setMilk(command.getMilk());
            orderRequest.setSize(command.getSize());
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest,headers) ;
            ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
            Order newOrder = newOrderResponse.getBody();
            System.out.println( newOrder );
            // pretty print JSON
            try {

                message = "\n" + "Starbucks Reserved Order" + "\n\n" +
                "Order Number: " + newOrder.getOrderNumber() + "\n" +
                "Drink: " + newOrder.getDrink() + "\n" +
                "Milk: " + newOrder.getMilk() + "\n" +
                "Size: " + newOrder.getSize() + "\n" +
                "Register: " + newOrder.getRegister() + "\n" +
                "Status: " + newOrder.getStatus() + "\n" +
                "Total: " + newOrder.getTotal() + "\n";
            }
            
            catch (Exception e) {}

        }
        if (action.equals("ACTIVATE CARD")) {
            message = "";
            resourceUrl = "http://"+API_HOST+"/card/activate/"+command.getCardnum()+"/"+command.getCardcode()+"?apikey="+API_KEY;
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> newCardRequest = new HttpEntity<String>(emptyRequest,headers) ;
            ResponseEntity<Card> newCardResponse = restTemplate.postForEntity(resourceUrl, newCardRequest, Card.class);
            Card newCard = newCardResponse.getBody();
            System.out.println( newCard );
            // pretty print JSON
            try {
                message = "\n" + "Starbucks Reserved Order" + "\n\n" +
                "Card Number: " + newCard.getCardNumber() + "\n" +
                "Card Code: " + newCard.getCardCode() + "\n" +
                "Balance: " + newCard.getBalance() + "\n" +
                "Activated: " + newCard.isActivated() + "\n" +
                "Status: " + newCard.getStatus() + "\n";

            }
            catch ( Exception e ) {}
        }

        if (action.equals("PAY")) {
            message = "";
            resourceUrl = "http://"+API_HOST+"/order/register/"+command.getRegister()+ "/pay/"+command.getCardnum()+"?apikey="+API_KEY; 
            System.out.println(resourceUrl) ;
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> paymentRequest = new HttpEntity<String>(emptyRequest,headers) ;
            ResponseEntity<Card> payForOrderResponse = restTemplate.postForEntity(resourceUrl, paymentRequest, Card.class);
            Card orderPaid = payForOrderResponse.getBody();
            System.out.println( orderPaid );
            // pretty print JSON
            try {
                 message = "\n" + "Starbucks Reserved Order" + "\n\n" +
                "Card Number: " + orderPaid.getCardNumber() + "\n" +
                "Card Code: " + orderPaid.getCardCode() + "\n" +
                "Balance: " + orderPaid.getBalance() + "\n" +
                "Activated: " + orderPaid.isActivated() + "\n" +
                "Status: " + orderPaid.getStatus() + "\n";
            }
            catch ( Exception e ) {}
        }
        
        else if ( action.equals("GET ORDER") ) {
            resourceUrl = "http://"+API_HOST+"/order/register/"+command.getRegister()+"?apikey="+API_KEY;;
            ResponseEntity<Order> getOrderResponse = restTemplate.getForEntity(resourceUrl, Order.class);
            Order getOrder = getOrderResponse.getBody();
            System.out.println( getOrder );
            Order makeOrder = new Order();
            String orderNumber = getOrder.getOrderNumber();
            makeOrder.setRegister(getOrder.getRegister());
            makeOrder.setOrderNumber( orderNumber);
            makeOrder.setDrink(getOrder.getDrink());
            makeOrder.setMilk(getOrder.getMilk());
            makeOrder.setSize(getOrder.getSize());
            makeOrder.setStatus("PAID");
            makeOrder.setTotal(getOrder.getTotal());
            orders.save(makeOrder);
            rabbit.convertAndSend(queue.getName(), orderNumber);

            // pretty print JSON
            try {
               message = "\n" + "Starbucks Reserved Order" + "\n\n" +
                "Order Number: " + makeOrder.getOrderNumber() + "\n" +
                "Drink: " + makeOrder.getDrink() + "\n" +
                "Milk: " + makeOrder.getMilk() + "\n" +
                "Size: " + makeOrder.getSize() + "\n" +
                "Register: " + makeOrder.getRegister() + "\n" +
                "Status: " + makeOrder.getStatus() + "\n" +
                "Total: " + makeOrder.getTotal() + "\n";
            }
            
            catch (Exception e) {}

        } 
        else if ( action.equals("CLEAR ORDER") ) {
            message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order"+ "\n" ;            
        }         
        command.setMessage( message ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;

    }
}



