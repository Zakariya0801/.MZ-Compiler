package i220801_E_i221194_E_Assignment1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DFA {
    Map<Set<State>, State> dfaStates = new HashMap<>();
    State startState;
    Set<State> finalStates = new HashSet<>();
    
    void printDFA() {
        System.out.println("DFA Representation:");
        System.out.println("Start State: " + startState.id);
        System.out.print("Final States: ");
        for (State s : finalStates) {
            System.out.print(s.id + " ");
        }
        System.out.println("\nTransitions:");
        
        for (Map.Entry<Set<State>, State> entry : dfaStates.entrySet()) {
            Set<State> nfaStates = entry.getKey();
            State dfaState = entry.getValue();
            
            for (State state : nfaStates) {
                for (Map.Entry<Character, List<State>> transition : state.transitions.entrySet()) {
                    char symbol = transition.getKey();
                    for (State nextState : transition.getValue()) {
                        System.out.println(dfaState.id + " -- " + symbol + " --> " + nextState.id);
                    }
                }
            }
        }
    }
}