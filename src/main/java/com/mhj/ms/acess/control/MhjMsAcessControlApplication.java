package com.mhj.ms.acess.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaServer
@EnableZuulProxy
public class MhjMsAcessControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MhjMsAcessControlApplication.class, args);
	}

}
