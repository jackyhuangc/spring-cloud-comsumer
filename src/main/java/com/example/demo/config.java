package com.example.demo;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
@Configuration
public class config {

	public config() {
		System.out.println("容器自动初始化");
	}

	@Bean //@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				System.out.println("正在处理跨域策略");
				registry.addMapping("/**").allowCredentials(true).allowedMethods("GET", "POST");
			}
		};
	}
	
	// 注册自定义的过滤器【然后向Spring注入这个Bean就行了】
    @Bean
    public AccessFilter accessFilter() {
    	
    	System.out.println("注册AccessFilter过滤器******");
        return new AccessFilter();
    } 
}