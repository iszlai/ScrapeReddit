package com.scrape.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.scrape")
@Configuration
@EnableAutoConfiguration
public class Main {
	public static void main(String[] args) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~$%@^*(&#*)@$@(*^$@&^~~~~~~~~~~~~~~~~~~~~~~~~~");
		//   ApplicationContext ctx = SpringApplication.run(Main.class, args);	
		   SpringApplication.run(Main.class, args);
	}	
}
