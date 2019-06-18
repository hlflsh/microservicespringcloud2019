package com.atguigu.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration  //ConfigBean(自定义一个类并在该类加上@Configuration注解后)=springcontext.xml
public class ConfigBean {
	/**
	 * RestTemplate提供了多种便捷访问远程Http服务的方法， 
	       是一种简单便捷的访问restful服务模板类，
	       是Spring提供的用于访问Rest服务的客户端模板工具集
	 *
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		
		return new RestTemplate();
	}
	@Bean
	public IRule myRule(){
		
		return new RandomRule();//随机算法
		//return new RoundRobinRule();//默认的轮询算法
		//return new RetryRule();//重试算法
	}
}
/*@Bean
	public UserService getUserService(){
		
		return new UserService();
	} == <bean id="userService" class="com.atguigu.service.UserService"><\bean>
 */