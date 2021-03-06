package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class ComputeService {
    
	@Autowired
    RestTemplate restTemplate;
    
	@HystrixCommand(fallbackMethod = "addServiceFallback",
			commandProperties = {
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5")
			})
    public String addService() {
        return restTemplate.getForEntity("http://Compute-Service/add?a=10&b=20", String.class).getBody();
    }
	
    public String addServiceFallback() {
        return "error";
    }
}