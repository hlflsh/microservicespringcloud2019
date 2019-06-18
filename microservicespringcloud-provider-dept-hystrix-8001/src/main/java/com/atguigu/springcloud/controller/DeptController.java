package com.atguigu.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class DeptController {
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {
		return deptService.add(dept);
	}
	
	@HystrixCommand(fallbackMethod="processHystrix_Get")
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = this.deptService.get(id);
		if(dept == null){
			throw new RuntimeException("该ID："+id+"没有没有对应的信息");
		}
		return dept;
	}
	
	public Dept processHystrix_Get(@PathVariable("id") Long id){
		
		Dept dept = new Dept();
		dept.setDeptno(id);
		dept.setDname("该ID："+id+"没有没有对应的信息,null--@HystrixCommand");
		dept.setDb_source("no this database in MySQL");
		return dept;
	}
	
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public List<Dept> list() {
		return deptService.list();
	}
	
	@RequestMapping(value="/dept/discoery",method=RequestMethod.GET)
	public Object discovery(){
		
		List<String> servicesList = discoveryClient.getServices();
		servicesList.forEach(e -> System.out.println(e));
		
		List<ServiceInstance> instances = discoveryClient.getInstances("MICROSERVICESPRINGCLOUD-DEPT");
		instances.forEach(e -> System.out.println(e.getServiceId()+"\t"+e.getHost()+"\t"+e.getPort()+"\t"+e.getUri()));
		return this.discoveryClient;
	}
	
	
	
	
}
