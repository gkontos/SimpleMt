package com.kontos.simplemt.model;

/**
 * representation of a linear axis
 * @author greg
 *
 */
public class Axis extends DataItem {

	private double positionActual;

	public double getPositionActual() {
		return positionActual;
	}

	public void setPositionActual(double positionActual) {
		this.positionActual = positionActual;
	}

	public String toString() {
		String s = "\"timestamp\":" + getTimestamp() + 
				 "\"sequenceNo\":" + getSequenceNo() + 
				 "\"positionActual\":"+ getPositionActual();
		return s;
	}
}
