package com.kontos.simplemt.model;

/*
 * representation of a rotary object ie, spindle
 */
public class Rotary extends DataItem {

	private double speedActual;

	public double getSpeedActual() {
		return speedActual;
	}

	public void setSpeedActual(double speed) {
		this.speedActual = speed;
	}
	
	
}
