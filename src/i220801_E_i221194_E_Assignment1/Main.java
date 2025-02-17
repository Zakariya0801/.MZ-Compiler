package i220801_E_i221194_E_Assignment1;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			String str = System.getProperty("user.dir") + "\\src\\i220801_E_i221194_E_Assignment1\\code.mz";
			System.out.println(str);
			String content = Files.readString(Path.of(str));
            System.out.println(content);
            
            String traditionalComments = "#[^#]*#";
            String endOfLineComments = "##[^\\r\\n]*\\r?\\n?";
            String identifiers = "[a-z][a-z]*";
            String numbers = "[0-9]+";
            String decimals = numbers+"\\."+numbers;
            String stringLiterals = "\\\"([^\\\"\\\\]|\\\\.)*\\\"";
            String keywords = "integer|returns|void|if|else|while|for|print|decimal|character";
            String operators = "=|\\+|\\-|\\*|/|%";
            String punctuation = "[(){};,]";
            
            LexicalAnalyzer lex = new LexicalAnalyzer(content);
            lex.addPattern(new TokenPattern(traditionalComments,TokenType.COMMENT));
            lex.addPattern(new TokenPattern(endOfLineComments,TokenType.COMMENT));
            lex.addPattern(new TokenPattern(punctuation,TokenType.PUNCTUATION));
            lex.addPattern(new TokenPattern(operators,TokenType.OPERATOR));
            lex.addPattern(new TokenPattern(keywords,TokenType.KEYWORD));
            lex.addPattern(new TokenPattern(identifiers,TokenType.IDENTIFIER));
            lex.addPattern(new TokenPattern(decimals,TokenType.DECIMAL));
            lex.addPattern(new TokenPattern(numbers,TokenType.NUMBER));
            lex.addPattern(new TokenPattern(stringLiterals,TokenType.STRINGLITERAL));
            List<Token> tokens = new ArrayList<>();
            SymbolTable table = new SymbolTable();
            while(!lex.hasEnded()) {
            	Token t = lex.nextToken();
            	if(t != null) {
            		System.out.println(t.toString());
            		tokens.add(t);
            		table.addtoSymbolTable(t);
            	}
            }
            
            table.printSymbolTable();
            
            String[] rules = {
            		"q0,a->q1",
            		"q0,b->q0",
            		"q1,a->q1",
            		"q1,b->q2",
            		"q2,a->q1",
            		"q2,b->q0"
            };
            String startState = "q0";
            String[] acceptingStates = {"q2"};
            
            LanguagePattern p = new LanguagePattern(new TokenPattern(traditionalComments,TokenType.COMMENT),rules,startState,acceptingStates);
            p.dfa.printTransitionTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
