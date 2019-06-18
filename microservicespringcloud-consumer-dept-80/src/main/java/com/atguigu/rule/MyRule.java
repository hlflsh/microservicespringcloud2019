package com.atguigu.rule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;

@Configuration
public class MyRule {
	
	@Bean
	public IRule mySelfRule(){
		
		return new MyselfRule();
	}
}
