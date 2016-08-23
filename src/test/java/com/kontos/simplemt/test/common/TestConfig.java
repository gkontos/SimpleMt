package com.kontos.simplemt.test.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
	@ComponentScan({ "com.kontos" })
	
	public class TestConfig {
		private static Logger LOG = LogManager.getLogger(TestConfig.class.getName());

}
