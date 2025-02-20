package i220801_E_i221194_E_Assignment1;

public class TokenPattern {
	String pattern;
	TokenType type;
	DFA dfa;
	public TokenPattern(String p, TokenType t){
		this.pattern = p;
		this.type = t;
		dfa = Main.createDFA(p);
	}
}