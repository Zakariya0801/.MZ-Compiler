package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
	private String input;
    private int currentPosition;
    List<TokenPattern> tokenPatterns;
    List<String >words = new ArrayList<>();
    int current = 0;
    List<TokenType> tokenTypes = List.of(TokenType.values());
    public LexicalAnalyzer(String input) {
        this.input = input;
        this.currentPosition = 0;
        tokenPatterns = new ArrayList<>();
        String[] wordstemp = input.split("[\\s\\r\\n]+");
        for(String word: wordstemp) {
        	words.add(word);
        }
    }
    
    public boolean hasEnded() {
    	return current >= words.size();
    }
    public void addPattern(TokenPattern pattern) {
    	tokenPatterns.add(pattern);
    }

    public Token nextToken() {
    	if(hasEnded()) return null;
    	String str = words.get(current);
        for (TokenPattern tokenPattern: tokenPatterns) {
        	System.out.println("checking "+ tokenPattern.pattern);
            if(tokenPattern.dfa.accepts(str)) {
            	current++;
            	return new Token(tokenPattern.type, str);
            }
        }
        return null;
    }
}