# CMPE 172 - Lab #7 Notes
# Cybersource
## Console
![cybersource](./images/spring-cybersource.png)
## Dashboard
![cybersource-dashboard](./images/cybersource.png)

# Spring Payment
## Payment Page
![payment-page](./images/payment-page.png)
## Missing Field
![missing-field-console](./images/missing-field-console.png)
![missing-field](./images/missing-field.png)
## Unsupported Card
![unsupported](./images/unsupported.png)

## Console
![console-view](./images/console-view.png)
## Accepted Payment
![accepted-payment](./images/accepted-payment.png)
## Cybersource Dashboard
![cybersource-payment](./images/cybersource-payement.png)

## MySQL terminal
![mysql](./images/mysql-terminal.png)


# Discussion
- Discussion of the Lombok, ThymeLeaf and Spring Features you used in this Lab. <br>
    Lombok is used in the PaymentCommand with the use of @Data. This will automatically generate @Getter,@Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor annotations on the class. So we don't have to manual write it <br>
![lombok](./images/lombok.png)
    Thymeleaf is used in the templates, creditcards.html. Thymeleaf is used to pass the user input to the backend server as a command with properties of  respective fields as firstname, lastname, cardnum, etc... 
![thymyleaf](./images/thymyleaf.png)<br>
- Discuss why Jackson is needed and where it is used in the code for this Lab.<br>
    Jackson is used to serialize/deserialized json response to java object. In this lab, Jackson is used in the Cybersource, AuthResponse/CaptureResp/RefundResponse. For this files, jackson capture the response from cybersource and deserialize the response into ids, check for the status, and display the error message.
![AuthResponse](./images/AuthResponse.png)
![CaptureRes](./images/CaptureResponse.png)
![Refund](./images/RefundResponse.png)