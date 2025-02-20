package i220801_E_i221194_E_Assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		String regex = "(a|b)(a|b)(a|b)(a|b)*";
		List<String> strs = ThompsonAlgorithm.extractParenthesesContent(regex);
		NFA nfa = ThompsonAlgorithm.compile(strs.get(0));
		System.out.println(strs.get(0));
		for(int i=1 ; i<strs.size() ; i++) {
			System.out.println(strs.get(i));
			nfa = ThompsonAlgorithm.concatCopy(nfa, ThompsonAlgorithm.compile(strs.get(i)));
		}
        
        
        DFA minDfa = nfa.toMinimizedDFA();
        
        System.out.println("\nMinimized DFA:");
        minDfa.display();
        
        System.out.println("DFA accepts 'a': " + minDfa.accepts("aa"));
        System.out.println("DFA accepts 'c': " + minDfa.accepts("c"));
        System.out.println("DFA accepts 'ab': " + minDfa.accepts("ab"));
        System.out.println("DFA accepts 'abb': " + minDfa.accepts("abb"));
	}

}
