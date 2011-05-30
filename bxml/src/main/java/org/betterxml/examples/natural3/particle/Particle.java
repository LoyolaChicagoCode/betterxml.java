package org.betterxml.examples.natural3.particle;

import org.betterxml.natural3.annotations.*;

@Element("Particle")
public class Particle {
	
	@Attribute("id")
	private String id;
	
	@Attribute("mass")
	private String mass;
	
	@Attribute("unit")
	private String unit;
	
	@Attribute("x")
	private String x;
	
	@Attribute("y")
	private String y;
	
	@Attribute("z")
	private String z;
	
	@Attribute("velocity")
	private String velocity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMass() {
		return mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getVelocity() {
		return velocity;
	}

	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}
	
}
