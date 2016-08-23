package com.kontos.simplemt.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * list of process state variables to capture a stream of data via the sample endpoint
 * @author greg
 *
 */
public class ProcessStateBuffer {
	private List<Axis> x;
	private List<Axis> y;
	private List<Axis> z;
	private List<Rotary> rotary;
	private List<SysCondition> hydraulic;
	private Long nextSequence;
	private Long firstSequence;
	
	public List<Axis> getX() {
		return x;
	}
	public void setX(List<Axis> x) {
		this.x = x;
	}
	public List<Axis> getY() {
		return y;
	}
	public void setY(List<Axis> y) {
		this.y = y;
	}
	public List<Axis> getZ() {
		return z;
	}
	public void setZ(List<Axis> z) {
		this.z = z;
	}
	public List<Rotary> getRotary() {
		return rotary;
	}
	public void setRotary(List<Rotary> rotary) {
		this.rotary = rotary;
	}
	public List<SysCondition> getHydraulic() {
		return hydraulic;
	}
	public void setHydraulic(List<SysCondition> hydraulic) {
		this.hydraulic = hydraulic;
	}
	public Long getNextSequence() {
		return nextSequence;
	}
	public void setNextSequence(Long nextSequence) {
		this.nextSequence = nextSequence;
	}
	public Long getFirstSequence() {
		return firstSequence;
	}
	public void setFirstSequence(Long firstSequence) {
		this.firstSequence = firstSequence;
	}
	
	/**
	 * display only the x and y parameters captured via the sample endpoint. 
	 * The x and y are presented to demonstrate capture of the variables.
	 * further variable display of a monolithic object would require establishing a method to group variables into a capture timeframe 
	 */
	public String toString() {
		// not guaranteed to reflect simultaneous positions 
		StringBuffer s = new StringBuffer("");
		int length = getX().size() < getY().size() ? getX().size() : getY().size();
		for (int i=0; i<length; i++) {
			s.append(String.format("{timestamp}="+
							getMaxDateTime(getX().get(i).getTimestamp(), 
							getY().get(i).getTimestamp()).toString()+
							";{x,y}={%8f,%8f}",
							getX().get(i).getPositionActual(), 
							getY().get(i).getPositionActual()) + "\n");
		}
		return s.toString();
	}
	
	public LocalDateTime getMaxDateTime(LocalDateTime x, LocalDateTime y) {
		return x.isAfter(y) ? x : y; 		
	}
}
