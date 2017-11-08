package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RequestMapping("/rest")
@RestController
public class RestTestController {

	@Autowired
	private KafkaReceiver receiver;
	
	// 默认是支持负载的？？？
	@Autowired
	private RestTemplate restTemplate;

	/*
	 * @Autowired private RestTemplateBuilder builder;
	 * 
	 * @Bean
	 * 
	 * @LoadBalanced //
	 * 添加负载均衡支持，很简单，只需要在RestTemplate上添加@LoadBalanced注解，那么RestTemplate即具有负载均衡的功能,
	 * 如果不加@LoadBalanced注解的话，会报java.net.UnknownHostException:xxxx异常，
	 * 此时无法通过注册到Eureka // Server上的服务名来调用服务，因为RestTemplate是无法从服务名映射到ip:port的，
	 * 映射的功能是由LoadBalancerClient来实现的。 public RestTemplate restTemplate() {
	 * return builder.build(); }
	 */
	
	@GetMapping("/template/{name}")
	@HystrixCommand(fallbackMethod = "hiError")
	public String findById(@PathVariable String name) {// 将原来的ip:port的形式，改成注册到Eureka
		// Server上的应用名即可
		// String str = restTemplate
		// .exchange("http://spring-reserevation-service/hello", HttpMethod.GET,
		// null, String.class, String.class)
		// .getBody();

		String str = this.restTemplate.getForObject("http://spring-reserevation-service/hello", String.class);
		System.out.println(str);
		// return u;

		return name + "testtest" + str;
	}

	@RequestMapping("/index")
	String index() {
		
		return "fffffffffffindex";
	}
	
	@RequestMapping("/getkafka")
	String getkafka() {
		
		return KafkaReceiver._Vote;
	}
	
	public String hiError(@PathVariable String name) {
		return "hi," + name + ",sorry,error!";
	}
}