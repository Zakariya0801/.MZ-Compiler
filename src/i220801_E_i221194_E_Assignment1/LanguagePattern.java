package i220801_E_i221194_E_Assignment1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LanguagePattern {
	TokenPattern RE;
	DFA dfa;
	public LanguagePattern(TokenPattern r, String[] rules, String startState ,String[] acceptingStates) {
		RE = r;
		dfa = createFromRules(rules,startState,acceptingStates);
	}
	
	private DFA createFromRules(String[] rules, String startStateName, String[] acceptingStates) {
		Map<String, State> stateMap = new HashMap<>();
		Set<String> acceptingStateSet = new HashSet<>(Arrays.asList(acceptingStates));
        
        // Create the start state
        State startState = new State(startStateName, acceptingStateSet.contains(startStateName));
        stateMap.put(startStateName, startState);
        
        DFA dfa = new DFA(startState);
        
        // Process each rule
        for (String rule : rules) {
            // Expected format: "currentState,input->nextState"
            String[] parts = rule.split("->");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid rule format: " + rule);
            }
            
            String[] leftParts = parts[0].split(",");
            if (leftParts.length != 2) {
                throw new IllegalArgumentException("Invalid rule format: " + rule);
            }
            
            String currentStateName = leftParts[0].trim();
            char input = leftParts[1].trim().charAt(0);
            String nextStateName = parts[1].trim();
            
            // Create states if they don't exist
            State currentState = stateMap.computeIfAbsent(currentStateName,
                name -> new State(name, acceptingStateSet.contains(name)));
            State nextState = stateMap.computeIfAbsent(nextStateName,
                name -> new State(name, acceptingStateSet.contains(name)));
            
            // Add states and transition to DFA
            dfa.addState(currentState);
            dfa.addState(nextState);
            dfa.addTransition(currentState, input, nextState);
        }
        
        return dfa;
    }
}
