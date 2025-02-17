package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
	private String input;
    private int currentPosition;
    List<TokenPattern> tokenPatterns;
    List<LanguagePattern> languagePatterns;
    
    List<TokenType> tokenTypes = List.of(TokenType.values());
    public LexicalAnalyzer(String input) {
        this.input = input;
        this.currentPosition = 0;
        languagePatterns = new ArrayList<>();
        tokenPatterns = new ArrayList<>();
    }
    
    public boolean hasEnded() {
    	return currentPosition >= input.length();
    }
    public void addPattern(TokenPattern pattern) {
    	tokenPatterns.add(pattern);
    	
    }
    public void addPattern(String r, String[] rules, String startState ,String[] acceptingStates) {
//    	languagePatterns.add(new LanguagePattern(r,rules,startState,acceptingStates));
    }

    public Token nextToken() {
    	Pattern patt = Pattern.compile("^[ \\t\\f\\r\\n]");
    	Matcher match = null;
    	do {
    		match = patt.matcher(input.substring(currentPosition, currentPosition+1));
    		if (match.find()) 
    			currentPosition += match.group().length();
    		
    		else break;
    	}while(!hasEnded());
        if (hasEnded()) {
        	System.out.println("Ended");
        	return null;
        }
        for (TokenPattern tokenPattern: tokenPatterns) {
            Pattern pattern = Pattern.compile("^" + tokenPattern.pattern);
            Matcher matcher = pattern.matcher(input.substring(currentPosition));
            if (matcher.find()) {
            	if(matcher.start() != 0) continue;
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(tokenPattern.type, value);
            }
        }
        
        return null;
    }
}
