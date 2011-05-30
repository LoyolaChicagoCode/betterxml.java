package org.betterxml.natural3;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inflector {
	
	//map from plural to singular irregulars
	private volatile static HashMap<String, String> irregular;
	private volatile static List<InflectorRule> patterns;
	
	private synchronized static void init() {
		irregular = new HashMap<String, String>();
		patterns = new ArrayList<InflectorRule>();
		
		//irregulars:
		irregular.put("people", "person");
		irregular.put("men", "man");
		irregular.put("children", "child");
		irregular.put("sexes", "sex");
		irregular.put("moves", "move");
		irregular.put("kine", "cow");
		
		//uncountables:
		irregular.put("equipment", "equipment");
		irregular.put("information", "information");
		irregular.put("rice", "rice");
		irregular.put("money", "money");
		irregular.put("species", "species");
		irregular.put("series", "series");
		irregular.put("fish", "fish");
		irregular.put("sheep", "sheep");
		
	
		//rule, replacement
		patterns.add(new InflectorRule("(.*)(quiz)zes$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(matr)ices$", "$1$2ix"));
		patterns.add(new InflectorRule("(.*)(vert|ind)ices$", "$1$2ex"));
		patterns.add(new InflectorRule("^(ox)en", "$1"));
		patterns.add(new InflectorRule("(.*)(alias|status)es$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(octop|vir)i$", "$1$2us"));
		patterns.add(new InflectorRule("(.*)(cris|ax|test)es$", "$1$2is"));
		patterns.add(new InflectorRule("(.*)(shoe)s$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(o)es$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(bus)es$", "$1$2"));
		patterns.add(new InflectorRule("(.*)([m|l])ice$", "$1$2ouse"));
		patterns.add(new InflectorRule("(.*)(x|ch|ss|sh)es$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(m)ovies$", "$1$2ovie"));
		patterns.add(new InflectorRule("(.*)(s)eries$", "$1$2eries"));
		patterns.add(new InflectorRule("(.*)([^aeiouy]|qu)ies$", "$1$2y"));
		patterns.add(new InflectorRule("(.*)([lr])ves$", "$1$2f")); //??
		patterns.add(new InflectorRule("(.*)(tive)s$", "$1$2"));
		patterns.add(new InflectorRule("(.*)(hive)s$", "$1$2"));
		patterns.add(new InflectorRule("(.*)([^f])ves$", "$1$2fe"));
		patterns.add(new InflectorRule("(.*)(^analy)ses$", "$1$2sis"));
		patterns.add(new InflectorRule("(.*)((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2$3sis"));
		patterns.add(new InflectorRule("(.*)([ti])a$", "$1$2um"));
		patterns.add(new InflectorRule("(.*)(n)ews$", "$1$2ews"));
		patterns.add(new InflectorRule("(.*)s$", "$1"));
		
	}
	
	private static class InflectorRule {
		private Pattern rule;
		private String substitution;
		
		public InflectorRule(String rule, String substitution) {
			this.rule = Pattern.compile(rule, Pattern.CASE_INSENSITIVE);
			this.substitution = substitution;
		}
		
		public String gsub(String s) {
			Matcher m = rule.matcher(s);
			if(m.matches())
				return m.replaceAll(substitution);
			return null;
		}
		
	}
	
	public static String singularize(String s) {
		if(irregular==null || patterns==null) {
			init();
		}
		
		if(irregular.containsKey(s)) {
			return irregular.get(s);
		}
		
		for(InflectorRule rule: patterns) {
			String replacement = rule.gsub(s);
			if(replacement!=null && replacement != "") 
				return replacement;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("Enter a plural word: ");
			String s = scanner.nextLine().trim();
			System.out.println(singularize(s));
		}
	}
}
