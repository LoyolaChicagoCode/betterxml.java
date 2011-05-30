package org.betterxml.examples.natural3.particle;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("InitialConditions")
public class InitialConditions {
	
	@Children(Particle.class)
	private ArrayList<Particle> particles = new ArrayList<Particle>(100);
	
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
}
