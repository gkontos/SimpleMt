package com.kontos.simplemt.service;

import java.io.IOException;
import java.io.InputStream;

import com.kontos.simplemt.model.ProcessState;

public interface MtConnectCurrent {

	/**
	 * get the current state of the agent
	 * @return
	 * @throws IOException
	 */
	ProcessState getCurrentState() throws IOException;
	/**
	 * get the current state of the agent for the intput stream given
	 * for testing
	 * @param is
	 * @return
	 */
	ProcessState getCurrentState(InputStream is);
}
