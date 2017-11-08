package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableDiscoveryClient
@EnableCircuitBreaker//使用EnableCircuitBreaker或者 EnableHystrix 表明Spring boot工程启用hystrix
@EnableHystrixDashboard//EnableHystrixDashboard注解表示启动对hystrix的监控
@EnableZuulProxy
@SpringBootApplication
public class SpringReservationConsumerApplication {

	/*
	 * @Autowired private RestTemplate restTemplate;
	 */
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

	/*
	 * @Bean
	 * 
	 * @LoadBalanced public RestTemplate restTemplate() {
	 * System.out.println("RestTemplate ****************************");
	 * RestTemplate restTemplate= new RestTemplate(); //restTemplate.set
	 * //restTemplate.setDefaultUriVariables(""); return restTemplate; }
	 */

	//@Primary
	@Bean
	@LoadBalanced// 将RestTemplate的bean标记为LoadBalancerClient，用作负载均衡，如果不加，将会报java.net.UnknownHostException错误
	RestTemplate restTemplate() {
		
		// 可以自定义负载策略
		return new RestTemplate();
	}

	// @Value("${my-config.appName}")
	// private String appName;

	@Value("${url}")
	private String url;

	@Bean
	CommandLineRunner runner(DiscoveryClient dc) {
		return args -> {
			dc.getInstances("spring-reservation-service").forEach(si -> System.out
					.println(String.format("Found %s %s:%s", si.getServiceId(), si.getHost(), si.getPort())));
		};
	}

	// @Autowired
	// void setEnviroment(Environment env) {
	// System.out.println("my-config.appName from env: "
	// + env.getProperty("my-config.appName"));
	// }

	@RequestMapping("/index")
	String index() {
		// String str = restTemplate
		// .exchange("http://spring-reserevation-service/hello", HttpMethod.GET,
		// null, String.class, String.class)
		// .getBody();

		/*
		 * DELETE delete GET getForObject getForEntity HEAD headForHeaders
		 * OPTIONS optionsForAllow POST postForLocation postForObject PUT put
		 * any exchange execute
		 */
		return "fffffffffff";
	}

	@RequestMapping("/index1")
	String index1() {

		return "ffffffffffffxxf" + url + "***";
	}

	@RequestMapping("/index2")
	Date index2() {
		return new Date();
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringReservationConsumerApplication.class, args);
	}
	
	// 注册自定义的过滤器【然后向Spring注入这个Bean就行了】
    @Bean
    public AccessFilter accessFilter() {
    	
    	System.out.println("注册AccessFilter过滤器******");
        return new AccessFilter();
    }    
}
