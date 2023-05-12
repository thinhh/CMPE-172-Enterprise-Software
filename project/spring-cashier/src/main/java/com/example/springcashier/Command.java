package com.example.springcashier;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Command {

    private String action ;
    private String message ;
    private String stores ;
    private String drink;
    private String milk;
    private String size;
    private String register ; 
    private String timestamp ;
    private String hash ;
}



