package com.example.springcashierworker;

import lombok.Data;
import lombok.RequiredArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Order_Drink")
@Data
@RequiredArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable=false)
    private String orderNumber;
    private String drink ;
    private String milk ;
    private String size ;
    private String total ;
    private String register ;
    private String status ;
    
    
}


/*

https://priceqube.com/menu-prices/%E2%98%95-starbucks

var DRINK_OPTIONS = [ "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino" ];
var MILK_OPTIONS  = [ "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" ];
var SIZE_OPTIONS  = [ "Short", "Tall", "Grande", "Venti", "Your Own Cup" ];

Caffè Latte
=============
tall    $2.95
grande  $3.65
venti   $3.95 (Your Own Cup)

Caffè Americano
===============
tall    $2.25
grande  $2.65
venti   $2.95 (Your Own Cup)

Caffè Mocha
=============
tall    $3.45
grande  $4.15
venti   $4.45 (Your Own Cup)

Cappuccino
==========
tall    $2.95
grande  $3.65
venti   $3.95 (Your Own Cup)

Espresso
========
short   $1.75
tall    $1.95

 */



