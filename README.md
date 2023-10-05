# SJSU CMPE 172 with Paul Nguyen
# Overall Class Description
## 1. Multiple Labs working wide-range of topics included: 
- Lab 1: Springboot / Intellij / Docker 
- Lab 2: Spring MVC / Lombok
- Lab 3: Spring Gumball using Spring MVC and running on Docker with LoadBalancer 
- Lab 4: Spring Gumball v2 with HMAC Encryption and integrating Spring Security 
- Lab 5: REST API and using POSTMAN as tool for testing API call 
- Lab 6: Spring Gumball v3 with integration of MySQL Hibernated and MySQL Database
- Lab 7:  Working with third-party Cybersource and Payment System
- Lab 8: Integrated KONG API Gateway into end-of-the-semester Starbucks Project
- Lab 9: Integrated RabbitMQ as a message-bus for end-of-the-semester Starbucks Project

## 2. End-of-the-semester Starbucks Projects under /project directory
### Requirement: 
  1. Cashier webpage that is able to:
  - Making API Calls to the Backend Starbucks API
  - Controller must process JSON responses from API and pass to View via Models 
  - Create New User/Log-in page with Spring Security   \
  2. MySQL Database: 
  - Store Orders from the Cashier API Call into the database
  - Store New/Existing User and the encrypted password 
  3. RabbitMQ
  - Retrieve "PAID" Order and put that Order onto the message queue
  - Create a worker class to pick-up that order and change the status to FULFILL to mimic an actual Starbucks worker that pick-up and make the drink
  - There will be a slight delay when the Order Status change from PAID to FULFILLED to mimic the amount of time it takes for the worker to create a drink
  4. Deployment on Google Cloud Platform (Already Taken Down)

## Link for Youtube Demo:
 https://youtu.be/kE-Xyy-sJRU



