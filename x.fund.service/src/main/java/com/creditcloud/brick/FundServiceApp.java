package com.creditcloud.brick;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@Slf4j
public class FundServiceApp 
{
	public static ConfigurableApplicationContext  context = null;

    public static void main( String[] args ) throws Exception
    {
    	//1. start application
        context = SpringApplication.run( FundServiceApp.class, args );
    }
}
