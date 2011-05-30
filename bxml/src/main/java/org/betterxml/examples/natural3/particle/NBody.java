package org.betterxml.examples.natural3.particle;


import java.util.ArrayList;

public class NBody {

	//TODO take care of case like here where we don't want a list but we just want one?
    private ArrayList<InitialConditions> particles;
	private ArrayList<Parameters> parameters;

	public ArrayList<InitialConditions> getParticles() {
		return particles;
	}

	public ArrayList<Parameters> getParameters() {
		return parameters;
	}

	public void addInitialCondition(InitialConditions particles) {
		this.particles.add(particles);
	}

	public void addParameter(Parameters parameters) {
		this.parameters.add(parameters);
	}
}
