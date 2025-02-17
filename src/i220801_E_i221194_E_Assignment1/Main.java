package i220801_E_i221194_E_Assignment1;

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
            String traditionalComments = "#*[^#]*#"; //Done
            String endOfLineComments = "##[^\\r\\n]*\\r?\\n?";//Done
            String identifiers = "[a-z][a-z]*";//Done
            String numbers = "[0-9]+";//Done
            String decimals = numbers+"\\."+numbers;//Done
            String stringLiterals = "\\\"([^\\\"\\\\]|\\\\.)*\\\"";//Done
            String keywords = "integer|returns|void|if|else|while|for|print|decimal|character|boolean";//Done
            String operators = "=|\\+|\\-|\\*|/|%";//Done
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
            	    "q0,x->q1",    // x goes to q1
            	    "q0,any->q0", // anything else goes to q2
            	    "q1,x->q1",    // stay in q1 for x
            	    "q1,any->q2", // go to q2 for non-x
            	    "q2,x->q1",    // go to q1 for x
            	    "q2,any->q2"  // stay in q2 for non-x
            	};

            String startState = "q0";
            String[] acceptingStates = {"q2"};
            LanguageInformation Info = new LanguageInformation();
            Info.addPatern(traditionalComments, TokenType.COMMENT, new String[] {
                    "q0,#->q1",
                    "q1,*->q2",
                    "q2,*->q3",
                    "q3,#->q4",
                    "q1,any->q0",
                    "q0,any->q0",
                    "q2,any->q2",
                    "q3,any->q2",  
                }, "q0", new String[] {"q4"} );
            
            Info.addPatern(endOfLineComments, TokenType.COMMENT, new String[] {
                    "q0,#->q1",
                    "q1,#->q2",
                    "q2,any->q2",
                    "q2,\\r->q3",
                    "q2,\\n->q3",  
                }, "q0", new String[] {"q3"} ); 
            
            Info.addPatern(identifiers, TokenType.IDENTIFIER, new String[] {
                    "q0,a-z->q1",
                    "q0,any->q2",
                    "q1,a-z->q1",
                    "q1,any->q2",
                    "q2,any->q2",
                }, "q0", new String[] {"q1"} );
            
            Info.addPatern(numbers, TokenType.NUMBER, new String[] {
                    "q0,0->q1",
                    "q0,1->q1",
                    "q0,2->q1",
                    "q0,3->q1",
                    "q0,4->q1",
                    "q0,5->q1",
                    "q0,6->q1",
                    "q0,7->q1",
                    "q0,8->q1",
                    "q0,9->q1",
                    
                    "q1,0->q1",
                    "q1,1->q1",
                    "q1,2->q1",
                    "q1,3->q1",
                    "q1,4->q1",
                    "q1,5->q1",
                    "q1,6->q1",
                    "q1,7->q1",
                    "q1,8->q1",
                    "q1,9->q1",
                    "q1,any->q2",
                    "q2,any->q2",
                    
                    
                    
                }, "q0", new String[] {"q1"} );
            
            Info.addPatern(decimals, TokenType.DECIMAL, new String[] {
                    "q0,0->q0",
                    "q0,1->q0",
                    "q0,2->q0",
                    "q0,3->q0",
                    "q0,4->q0",
                    "q0,5->q0",
                    "q0,6->q0",
                    "q0,7->q0",
                    "q0,8->q0",
                    "q0,9->q0",
                    
                    "q0,any->q3",
                    "q0,.->q1",
                    "q1,any->q3",

                    "q1,0->q2",
                    "q1,1->q2",
                    "q1,2->q2",
                    "q1,3->q2",
                    "q1,4->q2",
                    "q1,5->q2",
                    "q1,6->q2",
                    "q1,7->q2",
                    "q1,8->q2",
                    "q1,9->q2",
                    
                    "q2,0->q2",
                    "q2,1->q2",
                    "q2,2->q2",
                    "q2,3->q2",
                    "q2,4->q2",
                    "q2,5->q2",
                    "q2,6->q2",
                    "q2,7->q2",
                    "q2,8->q2",
                    "q2,9->q2",
                    "q2,any->q3",
                    
                    "q3,any->q3",
                                        
                }, "q0", new String[] {"q2"} );
            
            Info.addPatern(stringLiterals, TokenType.STRINGLITERAL, new String[] {
                    "q0,\"->q1",
                    "q1,any->q1",
                    "q1,\"->q2",  
                }, "q0", new String[] {"q2"} );
            
            Info.addPatern(keywords, TokenType.KEYWORD, new String[] {
                    "q0,integer->q1",
                    "q0,decimal->q1",
                    "q0,print->q1",
                    "q0,for->q1",
                    "q0,if->q1",
                    "q0,boolean->q1",
                    "q0,else->q1",
                    "q0,void->q1",
                    "q0,character->q1",
                    "q0,returns->q1",
                    "q0,while->q1",

                    "q1, ->q2",
                     
                }, "q0", new String[] {"q2"} );
            
            Info.addPatern(operators, TokenType.OPERATOR, new String[] {
                    "q0,+->q1",
                    "q0,-->q1",
                    "q0,/->q1",
                    "q0,*->q1",
                    "q0,%->q1",
                    "q0,=->q1",
                    "q1, ->q2",

                    "q0,any->q3",
                    "q3,any->q3",
                    
                }, "q0", new String[] {"q2"} );
            
            Info.addPatern(punctuation, TokenType.PUNCTUATION, new String[] {
                    "q0,.->q1",
                    "q0,;->q1",
                    "q0,{->q1",
                    "q0,(->q1",
                    "q0,}->q1",
                    "q0,)->q1",
                    
                    
                }, "q0", new String[] {"q1"} );
            
//            Info.addPatern(keywords, TokenType.KEYWORD, new String[] {
//                    "q0,\"->q1",
//                    "q1,any->q1",
//                    "q1,\"->q2",  
//                }, "q0", new String[] {"q2"} );
//            
            Info.print();
//            LanguagePattern p = new LanguagePattern(new TokenPattern(traditionalComments,TokenType.COMMENT),rules,startState,acceptingStates);
//            p.dfa.printTransitionTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
