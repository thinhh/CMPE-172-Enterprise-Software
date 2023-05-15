package com.example.springcashier;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springcashier.model.Card;
import com.example.springcashier.model.Ping;

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

@Slf4j
@Controller
@RequestMapping("/user/starbucks")
public class SpringCashierController {

    @Value("${starbucks.client.apikey}") String API_KEY ;
    @Value("${starbucks.client.apihost}") String API_HOST ;
    @Value("${starbucks.client.register}") String REGISTER ;
    @Autowired
    private OrderRepository orderRepository;

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
    /** public static Order GetNewOrder() {
        Order o = new Order();
        int x;
        String [] DRINK_OPTIONS = {"Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino"};
        String [] MILK_OPTIONS  = {"Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk"};
        String [][] SIZE_OPTIONS  = { {"Tall", "Grande", "Venti"}, {"Tall", "Grande", "Venti"}, {"Tall", "Grande", "Venti"}, {"Short", "Tall"}, {"Tall", "Grande", "Venti"}};
        String [][] PRICE_TOTAL = {{"$2.95", "$3.65", "$3.95"}, {"$2.25", "$2.65", "$2.95"},{"$3.45", "$4.15", "$4.45"}, {"$1.75", "$1.95"}, {"$2.95", "$3.65", "$3.95"}};
        Random rand = new Random();
        int random_drink = rand.nextInt(DRINK_OPTIONS.length);
        o.setDrink(command.getDrink());
        int random_milk = rand.nextInt(MILK_OPTIONS.length);
        o.setMilk(MILK_OPTIONS[random_milk]);
        int random_size;
        if (o.getDrink() != "Espresso"){
            random_size = rand.nextInt(3);
        }
        else {
            random_size= rand.nextInt(2);
        }
        o.setSize(SIZE_OPTIONS[random_drink][random_size]);
        o.setStatus("Ready for Payment");
        o.setTotal(PRICE_TOTAL[random_drink][random_size]);
        return o ;
    }*/


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
            resourceUrl = "http://"+API_HOST+"/ping";
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
                ObjectMapper objectMapper = new ObjectMapper() ;
                Object object = objectMapper.readValue(message, Object.class);
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                System.out.println(jsonString) ;
                message = "\n" + jsonString ;
            }
            catch ( Exception e ) {}
        }

        if (action.equals("NEW CARD")) {
            message = "";
            resourceUrl = "http://"+API_HOST+"/cards";
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> newCardRequest = new HttpEntity<String>(emptyRequest, headers) ;
            ResponseEntity<Card> newCardResponse = restTemplate.postForEntity(resourceUrl, newCardRequest, Card.class);
            Card newCard = newCardResponse.getBody();
            System.out.println( newCard );
            // pretty print JSON
            try {
                ObjectMapper objectMapper = new ObjectMapper() ;
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newCard);
                System.out.println( jsonString) ;
                message = "\n" + jsonString ;
            }
            catch ( Exception e ) {}
        }
        /* Process Post Action */
        if ( action.equals("Place Order") ) {
            Order order = new Order();
            order.setRegister( command.getRegister()) ;
            order.setDrink(command.getDrink());
            order.setMilk(command.getMilk());
            order.setSize(command.getSize());
            order.setStatus("Ready for Payment");
            String price = "0.0";
            switch (order.getDrink()) {
                case "Caffe Latte":
                    switch (order.getSize()) {
                        case "Tall":
                            price = "2.95";
                            break;
                        case "Grande":
                            price = "3.65";
                            break;
                        case "Venti":
                        case "Your Own Cup":
                            price = "3.95";
                            break;
                        default:
                           order.setSize("Invalid Size");
                    }
                    break;
                case "Caffe Americano":
                    switch (order.getSize()) {
                        case "Tall":
                            price = "2.25";
                            break;
                        case "Grande":
                            price = "2.65";
                            break;
                        case "Venti":
                        case "Your Own Cup":
                            price = "2.95";
                            break;
                        default:
                           order.setSize("Invalid Size");
                }
                    break;
                case "Caffe Mocha":
                    switch (order.getSize()) {
                        case "Tall":
                            price = "3.45";
                            break;
                        case "Grande":
                            price = "4.15";
                            break;
                        case "Venti":
                        case "Your Own Cup":
                            price = "4.45";
                            break;
                        default:
                            order.setSize("Invalid Size");
                }
                    break;
                case "Espresso":
                    switch (order.getSize()) {
                        case "Short":
                            price = "1.75";
                            break;
                        case "Tall":
                            price = "1.95";
                            break;
                        default:
                            order.setSize("Invalid Size");
                    }
                    break;
                case "Cappuccino":
                    switch (order.getSize()) {
                        case "Tall":
                            price = "2.95";
                            break;
                        case "Grande":
                            price = "3.65";
                            break;
                        case "Venti":
                        case "Your Own Cup":
                            price = "3.95";
                            break;
                        default:
                            order.setSize("Invalid Size");
                    }
                    break;
                default:
                    order.setDrink("Invalid Drink");;
        }
            order.setTotal(price);
            message = "Starbucks Reserved Order" + "\n\n" +
                "Drink: " + order.getDrink() + "\n" +
                "Milk:  " + order.getMilk() + "\n" +
                "Size:  " + order.getSize() + "\n" +
                "Total: " + order.getTotal() + "\n" +
                "\n" +
                "Register: " + order.getRegister() + "\n" +
                "Status:   " + order.getStatus() + "\n" ;
            orderRepository.save(order);

        }
        else if ( action.equals("Get Order") ) {
            message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order"+ "\n" ;            
        } 
        else if ( action.equals("Clear Order") ) {
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



