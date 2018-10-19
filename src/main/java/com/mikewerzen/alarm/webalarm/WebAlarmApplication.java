package com.mikewerzen.alarm.webalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mikewerzen"})
public class WebAlarmApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(WebAlarmApplication.class, args);
	}
}
