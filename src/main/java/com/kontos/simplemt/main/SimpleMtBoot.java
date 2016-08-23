package com.kontos.simplemt.main;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.kontos")
public class SimpleMtBoot {
	private static Logger LOG = LogManager.getLogger(SimpleMtBoot.class.getName());
	@Autowired
	private SimpleMtRun simpleMt;
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(SimpleMtBoot.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
 
		SimpleMtBoot proc = context.getBean(SimpleMtBoot.class);
		proc.init();
		proc.launch();
	}

	/**
	 * init method for configurations
	 */
	private void init() {
	}
	
	/**
	 * start the agent
	 */
	private void launch() {
		
		try {
			simpleMt.consumeAgent();
		} catch (IOException e) {
			LOG.error(e);
		}
		
	}
}
