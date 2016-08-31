package com.example;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.model.Order;

@RestController
public class OrderLBAPI {

 @Autowired
 private LoadBalancerClient loadBalancerClient;

 @SuppressWarnings("unchecked")
 @RequestMapping(value="/",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
 public List<Order> findAll(){
	 ServiceInstance serviceInsatnce = loadBalancerClient.choose("order-microservice");
	 try {
		     return(new RestTemplate()).getForObject(new URI(serviceInsatnce.getUri().toString().concat("/api/order")),List.class);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	 return null;	 
 }
 
 

}
