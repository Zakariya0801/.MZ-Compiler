package i220801_E_i221194_E_Assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static DFA createDFA(String regex) {
		List<String> strs = ThompsonAlgorithm.extractParenthesesContent(regex);
		NFA nfa = ThompsonAlgorithm.compile(strs.get(0));
		for(int i=1 ; i<strs.size() ; i++) {
			nfa = ThompsonAlgorithm.concatCopy(nfa, ThompsonAlgorithm.compile(strs.get(i)));
		}
		DFA minDfa = nfa.toMinimizedDFA();
		return minDfa;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		
			String str = System.getProperty("user.dir") + "\\src\\i220801_E_i221194_E_Assignment1\\code.mz";
			System.out.println(str);
			String content;
			content = Files.readString(Path.of(str));
	        System.out.println(content);
	        
	        
	        LexicalAnalyzer lex = new LexicalAnalyzer (content);
	        String traditionalComments = "(#&~*&#)";
	        
	        String identifiers = "([a-z])([a-z])*";
	        String numbers = "([0-9])([0-9])*";
	        String decimals = numbers+"."+numbers;
	        String stringLiterals = "(\"~\")";
	        String keywords = "(integer|returns|void|if|else|while|for|print|decimal|character)";
	        String operators = "(=|+|-|/|%)";
	        String punctuation = "({|}|;|,)";
	        System.out.println("asfja");
	        lex.addPattern(new TokenPattern(traditionalComments, TokenType.COMMENT));
//	        lex.addPattern(new TokenPattern(endOfLineComments, TokenType.COMMENT));
	        lex.addPattern(new TokenPattern(identifiers, TokenType.IDENTIFIER));
//	        lex.addPattern(new TokenPattern(numbers, TokenType.NUMBER));
//	        lex.addPattern(new TokenPattern(decimals, TokenType.DECIMAL));
//	        lex.addPattern(new TokenPattern(stringLiterals, TokenType.STRINGLITERAL));
//	        lex.addPattern(new TokenPattern(keywords, TokenType.KEYWORD));
//	        lex.addPattern(new TokenPattern(operators, TokenType.OPERATOR));
//	        lex.addPattern(new TokenPattern(punctuation, TokenType.PUNCTUATION));
	        while(!lex.hasEnded()) {
	        	Token t = lex.nextToken();
	        	if(t != null)
	        		System.out.println(t.toString());	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
