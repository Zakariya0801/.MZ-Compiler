package i220801_E_i221194_E_Assignment1;
import java.util.*;

public class DFA {
    public ArrayList<Integer> states;
    public ArrayList<Trans> transitions;
    public int initial_state;
    public HashSet<Integer> final_states;
    public HashSet<Character> alphabet;
    
    public DFA() {
        this.states = new ArrayList<Integer>();
        this.transitions = new ArrayList<Trans>();
        this.initial_state = 0;
        this.final_states = new HashSet<Integer>();
        this.alphabet = new HashSet<Character>();
    }
    
    public void setStateSize(int size) {
        for (int i = 0; i < size; i++)
            this.states.add(i);
    }
    
    public void display() {
        System.out.println("DFA Transitions:");
        for (Trans t: transitions) {
            System.out.println("(" + t.state_from + ", " + t.trans_symbol + 
                ", " + t.state_to + ")");
        }
        System.out.println("Final states: " + final_states);
    }
    
    // Check if a string is accepted by this DFA
    public boolean accepts(String input) {
        int currentState = initial_state;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            boolean transitionFound = false;
            boolean anytransition = false;
            int tempstate = 0;
            for (Trans t : transitions) {
            	if (t.state_from == currentState && (t.trans_symbol == c) ) {
            		currentState = t.state_to;
            		transitionFound = true;
            		break;
            	}
            	if(t.state_from == currentState && (t.trans_symbol == '~') ) {
            		anytransition = true;
            		tempstate = t.state_to;
            	}
            }
            
            if (!transitionFound) {
            	if(anytransition) {
            		currentState = tempstate;
            	}
            	else
            		return false; // No valid transition for this character
            }
        }
        
        return final_states.contains(currentState);
    }
}