package com.example.springcashier;
import org.springframework.beans.factory.annotation.Autowired;

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
     public static Order GetNewOrder() {
        Order o = new Order();
        int x;
        String [] DRINK_OPTIONS = {"Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino"};
        String [] MILK_OPTIONS  = {"Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk"};
        String [][] SIZE_OPTIONS  = { {"Tall", "Grande", "Venti"}, {"Tall", "Grande", "Venti"}, {"Tall", "Grande", "Venti"}, {"Short", "Tall"}, {"Tall", "Grande", "Venti"}};
        String [][] PRICE_TOTAL = {{"$2.95", "$3.65", "$3.95"}, {"$2.25", "$2.65", "$2.95"},{"$3.45", "$4.15", "$4.45"}, {"$1.75", "$1.95"}, {"$2.95", "$3.65", "$3.95"}};
        Random rand = new Random();
        int random_drink = rand.nextInt(DRINK_OPTIONS.length);
        o.setDrink(DRINK_OPTIONS[random_drink]);
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
    }


    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

        String message = "" ;

        log.info( "Action: " + action ) ;
        command.setRegister( command.getStores() ) ;
        log.info( "Command: " + command ) ;

        /* Process Post Action */
        if ( action.equals("Place Order") ) {
            Order order = GetNewOrder() ;
            order.setRegister( command.getRegister() ) ;
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



