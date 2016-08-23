package com.kontos.simplemt.model;

import java.time.LocalDateTime;

/**
 * The process state object to include variables of interest for monolithic display
 * @author greg
 *
 */
public class ProcessState {
	private Axis x;
	private Axis y;
	private Axis z;
	private Rotary rotary;
	private SysCondition hydraulic;
	private Long nextSequence;
	private Long firstSequence;
	
	public Axis getX() {
		return x;
	}
	public void setX(Axis x) {
		this.x = x;
	}
	public Axis getY() {
		return y;
	}
	public void setY(Axis y) {
		this.y = y;
	}
	public Axis getZ() {
		return z;
	}
	public void setZ(Axis z) {
		this.z = z;
	}
	public SysCondition getHydraulic() {
		return hydraulic;
	}
	public void setHydraulic(SysCondition hydraulic) {
		this.hydraulic = hydraulic;
	}
	public Rotary getRotary() {
		return rotary;
	}
	public void setRotary(Rotary rotary) {
		this.rotary = rotary;
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
	
	public String toString() {
		LocalDateTime max = getTimestamp();
		String s = "{timestamp}="+max.toString()+";{x,y,z}={"+getX().getPositionActual()+","+getY().getPositionActual()+","+getZ().getPositionActual()+"};" + 
					"{spindle speed}={"+getRotary().getSpeedActual()+"};{hydraulic pressure}={"+getHydraulic().getCondition()+"}";
		return s;
	}
	
	public LocalDateTime getTimestamp() {
		LocalDateTime max = getX().getTimestamp();
		if (getY().getTimestamp().isAfter(max)) {
			max = getY().getTimestamp();
		}
		if (getZ().getTimestamp().isAfter(max)) {
			max = getZ().getTimestamp();
		}
		if (getRotary().getTimestamp().isAfter(max)) {
			max = getRotary().getTimestamp();
		}
		if (getHydraulic().getTimestamp().isAfter(max)) {
			max = getHydraulic().getTimestamp();
		}
		return max;
	}
	
}
