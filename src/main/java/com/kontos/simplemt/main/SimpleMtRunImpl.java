package com.kontos.simplemt.main;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.ProcessStateBuffer;
import com.kontos.simplemt.service.MtConnectCurrent;
import com.kontos.simplemt.service.MtConnectSample;
import com.kontos.simplemt.util.ProcessStateUtils;

@Component
public class SimpleMtRunImpl implements SimpleMtRun {
	private static Logger LOG = LogManager.getLogger(SimpleMtRunImpl.class.getName());
	
	@Autowired
	MtConnectCurrent mtConnectCurrent;
	@Autowired
	MtConnectSample mtConnectSample;
	@Autowired
	ProcessStateUtils processStateUtils;
	boolean run = true;
	
	public void consumeAgent() throws IOException {
		ProcessState current = null;
		try {
			current = mtConnectCurrent.getCurrentState();
		} catch (Exception e) {
			throw new IOException("Unable to get current agent state.", e);
		}
		ProcessStateBuffer updateState = null;
		
		System.out.println(current.toString());
		while (run) {
			try {
				LocalDateTime time = current.getTimestamp();
				updateState = mtConnectSample.updateState(current);
				// output all values of updateState
				System.out.println(updateState.toString());
				// set the current value to the most updates values of the buffer
				current = processStateUtils.updateProcessState(updateState, current);
				if (current.getTimestamp().equals(time)) {
					System.out.println("No update.");
				} else {
					System.out.println(current.toString());
				}
		
			} catch (Exception e) {
				LOG.error("Error caught.  Waiting for 1 sec", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					LOG.error("quitting", e1);
					run = false;
				}
				
			}
		}
	}

}
