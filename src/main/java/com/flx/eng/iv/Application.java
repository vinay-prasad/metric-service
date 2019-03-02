package com.flx.eng.iv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	static ConfigurableApplicationContext context = null;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
	}

	static void stop() {
		if (null != context) {
			context.stop();
			context.close();
			context = null;
		}
	}
}
