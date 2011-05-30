package org.betterxml.natural3;

import static org.junit.Assert.*;
import org.junit.Test;


public class InflectorTest {

	
	
	@Test
	public void testIrregulars() {
		assertEquals(Inflector.singularize("people"), "person");
		assertEquals(Inflector.singularize("people"), "person");
		assertEquals(Inflector.singularize("people"), "person");
		assertEquals(Inflector.singularize("men"), "man");
		assertEquals(Inflector.singularize("children"), "child");
		assertEquals(Inflector.singularize("sexes"), "sex");
		assertEquals(Inflector.singularize("moves"), "move");
		assertEquals(Inflector.singularize("kine"), "cow");
	}
	
	@Test
	public void testUncountable() {
		assertEquals(Inflector.singularize("equipment"), "equipment");
		assertEquals(Inflector.singularize("information"), "information");
		assertEquals(Inflector.singularize("rice"), "rice");
		assertEquals(Inflector.singularize("money"), "money");
		assertEquals(Inflector.singularize("species"), "species");
		assertEquals(Inflector.singularize("series"), "series");
		assertEquals(Inflector.singularize("fish"), "fish");
		assertEquals(Inflector.singularize("sheep"), "sheep");
	}
	
	@Test
	public void testRules() {
		//this test is not really for grammatical compliance but
		//rather for consisxtency with the RoR inflector
		//see http://nubyonrails.com/tools/pluralize
		assertEquals("quiz", Inflector.singularize("quizzes"));
		assertEquals("matrix", Inflector.singularize("matrices"));
		assertEquals("vertex", Inflector.singularize("vertices"));
		assertEquals("ox", Inflector.singularize("oxen"));
		assertEquals("alias", Inflector.singularize("aliases"));
		assertEquals("octopus", Inflector.singularize("octopi"));
		assertEquals("virus", Inflector.singularize("viri"));
		assertEquals("axis", Inflector.singularize("axes"));
		assertEquals("shoe", Inflector.singularize("shoes"));
		assertEquals("horseshoe", Inflector.singularize("horseshoes"));
		assertEquals("ho", Inflector.singularize("hoes"));
		assertEquals("bus", Inflector.singularize("buses"));
		assertEquals("omnibus", Inflector.singularize("omnibuses"));
		assertEquals("mouse", Inflector.singularize("mice"));
		assertEquals("beach", Inflector.singularize("beaches"));
		assertEquals("movie", Inflector.singularize("movies"));
		assertEquals("aseries", Inflector.singularize("aseries"));
		assertEquals("py", Inflector.singularize("pies"));
		assertEquals("leafe", Inflector.singularize("leaves")); //arrgh
		assertEquals("knife", Inflector.singularize("knives"));
		assertEquals("starf", Inflector.singularize("starves"));
		assertEquals("beehive", Inflector.singularize("beehives"));
		assertEquals("analysis", Inflector.singularize("analyses"));
		assertEquals("diagnosis", Inflector.singularize("diagnoses"));
		assertEquals("parenthesis", Inflector.singularize("parentheses"));
		assertEquals("synopsis", Inflector.singularize("synopses"));
		assertEquals("thesis", Inflector.singularize("theses"));
		assertEquals("planetium", Inflector.singularize("planetia"));
		assertEquals("barnacle", Inflector.singularize("barnacles"));
		assertEquals("motive", Inflector.singularize("motives"));
		assertEquals("votive", Inflector.singularize("votives"));
		
	}
}
