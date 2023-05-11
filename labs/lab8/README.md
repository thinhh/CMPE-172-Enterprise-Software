# CMPE 172 - Lab #8 Notes
# KONG API on POSTMAN
## KONG console
![kong-console](./images/kong-docker-console.png)
## STARBUCK API
![starbucks](./images/starbuck-api.png)
## POSTMAN 
![1](./images/postman-1.png)
![2](./images/postman-2.png)
![3](./images/postman-3.png)
![4](./images/postman-4.png)
![5](./images/postman-5.png)
![6](./images/postman-6.png)
![7](./images/postman-7.png)
![8](./images/postman-8.png)
![9](./images/postman-9.png)
![10](./images/postman-10.png)
![11](./images/postman-11.png)
![12](./images/postman-12.png)
![13](./images/postman-13.png)
![14](./images/postman-14.png)
# KONG API GKE
![g0](./images/gke-0.png)
![g1](./images/gke-1.png)
![g2](./images/gke-2.png)
![g3](./images/gke-3.png)
![g4](./images/gke-4.png)
![g5](./images/gke-5.png)
![g6](./images/gke-6.png)
![g7](./images/gke-7.png)

# Discusion 
- Any challenges you face while working on this lab (i.e. GKE deployment issues) and how you overcame / solved them. <br>
 1. Unable to download httpie / Solution use cURL instead
 2. Misunderstood about the image push to docker with wrong name/ Solution: Log on to my docker and check the image name in the repo that I pushed.
 3. Using incorrect docker command to push my image / Solution: Go to docker page to check the requirement to push the image. Turn out I was missing the -t for tag
 4. Using wrong port for service, unable to ping starbucks-api/ Solution: recheck the port on GKE
 5. Unable to create ingress because different version takes in different parament name for ServiceName and ServicePort /Solution: turns out to be service.name and then service.port.number.  
    <br>
- A discussion of what changes would be needed in order to deploy your Starbucks API with MySQL / Cloud SQL. <br>
    In order to deploy mySQL, I need to include mysql in the project application.property. Create the mySQL with specific setting on GKE. And then create a SESSION schema for jdbc on GKE.   