package org.linker.productor;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
public class ProductorApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProductorApplication.class).web(true).run(args);
	}
}
