package com.kontos.simplemt.main;

import java.io.IOException;

public interface SimpleMtRun {
	
	/**
	 * connect to an agent, consume, and display process variables
	 * @throws IOException 
	 */
	void consumeAgent() throws IOException;
}
