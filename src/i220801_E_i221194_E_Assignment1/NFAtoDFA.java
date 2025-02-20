package i220801_E_i221194_E_Assignment1;
import java.util.*;

public class NFAtoDFA {
    
    // Helper method to compute epsilon closure of a state
    private static HashSet<Integer> epsilonClosure(NFA nfa, int state) {
        HashSet<Integer> closure = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(state);
        closure.add(state);
        
        while (!stack.isEmpty()) {
            int current = stack.pop();
            
            for (Trans t : nfa.transitions) {
                if (t.state_from == current && t.trans_symbol == 'E') {
                    if (!closure.contains(t.state_to)) {
                        closure.add(t.state_to);
                        stack.push(t.state_to);
                    }
                }
            }
        }
        
        return closure;
    }
    
    // Helper method to compute epsilon closure of a set of states
    private static HashSet<Integer> epsilonClosure(NFA nfa, HashSet<Integer> states) {
        HashSet<Integer> closure = new HashSet<>();
        
        for (int state : states) {
            closure.addAll(epsilonClosure(nfa, state));
        }
        
        return closure;
    }
    
    // Helper method to get all possible input symbols (excluding epsilon)
    private static HashSet<Character> getAlphabet(NFA nfa) {
        HashSet<Character> alphabet = new HashSet<>();
        
        for (Trans t : nfa.transitions) {
            if (t.trans_symbol != 'E') {
                alphabet.add(t.trans_symbol);
            }
        }
        
        return alphabet;
    }
    
    // Helper method to compute next states after a transition
    private static HashSet<Integer> move(NFA nfa, HashSet<Integer> states, char symbol) {
        HashSet<Integer> nextStates = new HashSet<>();
        
        for (int state : states) {
            for (Trans t : nfa.transitions) {
                if (t.state_from == state && t.trans_symbol == symbol) {
                    nextStates.add(t.state_to);
                }
            }
        }
        
        return nextStates;
    }
    
    // Main conversion method
    public static DFA convert(NFA nfa) {
        DFA dfa = new DFA();
        
        // Get the alphabet (all possible input symbols)
        HashSet<Character> alphabet = getAlphabet(nfa);
        dfa.alphabet = alphabet;
        
        // Map to store DFA state numbers for sets of NFA states
        HashMap<HashSet<Integer>, Integer> dfaStates = new HashMap<>();
        
        // Queue for processing sets of NFA states
        Queue<HashSet<Integer>> queue = new LinkedList<>();
        
        // Start with epsilon closure of the initial state
        HashSet<Integer> initialClosure = epsilonClosure(nfa, 0);
        queue.add(initialClosure);
        dfaStates.put(initialClosure, 0);
        dfa.states.add(0);
        
        // Check if the initial state is also a final state
        if (initialClosure.contains(nfa.final_state)) {
            dfa.final_states.add(0);
        }
        
        while (!queue.isEmpty()) {
            HashSet<Integer> currentStates = queue.poll();
            int dfaState = dfaStates.get(currentStates);
            
            // Process each input symbol
            for (char symbol : alphabet) {
                // Get next states after moving with this symbol
                HashSet<Integer> nextStates = move(nfa, currentStates, symbol);
                
                // Apply epsilon closure to these states
                nextStates = epsilonClosure(nfa, nextStates);
                
                // Skip if no next states
                if (nextStates.isEmpty()) {
                    continue;
                }
                
                // Add as a new DFA state if not already processed
                if (!dfaStates.containsKey(nextStates)) {
                    int newStateNumber = dfaStates.size();
                    dfaStates.put(nextStates, newStateNumber);
                    dfa.states.add(newStateNumber);
                    queue.add(nextStates);
                    
                    // Check if this is a final state
                    if (nextStates.contains(nfa.final_state)) {
                        dfa.final_states.add(newStateNumber);
                    }
                }
                
                // Add the transition
                int nextDfaState = dfaStates.get(nextStates);
                dfa.transitions.add(new Trans(dfaState, nextDfaState, symbol));
            }
        }
        
        return dfa;
    }
}