package com.kontos.simplemt.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.ProcessStateBuffer;

/**
 * utilies for processing ProcessState
 * @author greg
 *
 */
@Component
public class ProcessStateUtils {

	private static Logger LOG = LogManager.getLogger(ProcessStateUtils.class.getName());
	
	/**
	 * Get the most recent process variable and create a 'current' processState object
	 * @param buffer
	 * @param previous
	 * @return
	 */
	public ProcessState updateProcessState(ProcessStateBuffer buffer, ProcessState previous) {
		ProcessState current = new ProcessState();
		current.setFirstSequence(buffer.getFirstSequence());
		current.setNextSequence(buffer.getNextSequence());
		
		if (buffer.getHydraulic() != null && buffer.getHydraulic().size() > 0) {
			current.setHydraulic(buffer.getHydraulic().get(buffer.getHydraulic().size()-1));
		} else {
			current.setHydraulic(previous.getHydraulic());	
		}
		
		if (buffer.getRotary() != null && buffer.getRotary().size() > 0) {
			current.setRotary(buffer.getRotary().get(buffer.getRotary().size()-1));
		} else {
			current.setRotary(previous.getRotary());	
		}
		
		if (buffer.getX() != null && buffer.getX().size() > 0) {
			current.setX(buffer.getX().get(buffer.getX().size()-1));
		} else {
			current.setX(previous.getX());	
		}
		
		if (buffer.getY() != null && buffer.getY().size() > 0) {
			current.setY(buffer.getY().get(buffer.getY().size()-1));
		} else {
			current.setY(previous.getY());	
		}
		
		if (buffer.getZ() != null && buffer.getZ().size() > 0) {
			current.setZ(buffer.getZ().get(buffer.getZ().size()-1));
		} else {
			current.setZ(previous.getZ());	
		}
		return current;
	}
	
	
}
