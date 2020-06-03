package com.aim.project.pwp.instance;


public class Location {

	private final double x;
	
	private final double y;

	public Location(double x, double y) {

		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "("+ this.x + ", " + this.y +")";
	}
}
