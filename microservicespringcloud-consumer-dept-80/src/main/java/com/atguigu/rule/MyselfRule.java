package com.atguigu.rule;

import java.util.List;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class MyselfRule extends AbstractLoadBalancerRule {
	
	private int count = 0;//表示调用五次后切换服务
	private int currentIndex = 0;//当前指针
	
	public Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
			return null;
		}
		Server server = null;

		while (server == null) {
			if (Thread.interrupted()) {
				return null;
			}
			List<Server> upList = lb.getReachableServers();
			List<Server> allList = lb.getAllServers();

			int serverCount = allList.size();
			if (serverCount == 0) {
				/*
				 * No servers. End regardless of pass, because subsequent passes
				 * only get more restrictive.
				 */
				return null;
			}
			
			if(count < 5){
				server = upList.get(currentIndex);
				count++;
			}else{
				count = 0;
				currentIndex++;
				if(currentIndex >= upList.size()){
					currentIndex = 0;
				}
			}

			//server = upList.get(index);

			if (server == null) {
				/*
				 * The only time this should happen is if the server list were
				 * somehow trimmed. This is a transient condition. Retry after
				 * yielding.
				 */
				Thread.yield();
				continue;
			}

			if (server.isAlive()) {
				return (server);
			}

			// Shouldn't actually happen.. but must be transient or a bug.
			server = null;
			Thread.yield();
		}

		return server;

	}

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		// TODO Auto-generated method stub

	}
}