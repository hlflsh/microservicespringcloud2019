package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.atguigu.rule.MyRule;

@SpringBootApplication
@EnableEurekaClient
//在启动该微服务的时候就能去加载我们自定义Ribbon配置类，从而使配置生效
@RibbonClient(name="MICROSERVICESPRINGCLOUD-DEPT",configuration=MyRule.class)
public class DeptConsumer80_App {

	public static void main(String[] args) {
		
		SpringApplication.run(DeptConsumer80_App.class, args);
	}

}
