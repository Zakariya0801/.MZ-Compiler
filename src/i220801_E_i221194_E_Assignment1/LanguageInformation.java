package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;

public class LanguageInformation {
	List<LanguagePattern> patterns;
	
	public LanguageInformation() {
		patterns = new ArrayList<>();
	}
	
	public void addPatern(String RE,TokenType type, String[] rules, String startState ,String[] acceptingStates) {
		patterns.add(new LanguagePattern(new TokenPattern(RE,type),rules,startState,acceptingStates));
	}
	
	public void print() {
		for(LanguagePattern p: patterns) {
			System.out.println(p.RE.pattern);
			p.dfa.printTransitionTable();
			System.out.println();
		}
	}
	
}
