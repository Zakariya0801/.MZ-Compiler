package i220801_E_i221194_E_Assignment1;

import java.util.*;

public class NFAAcceptor {

    public static boolean acceptsString(NFA nfa, String input) {
        Set<Integer> currentStates = epsilonClosure(nfa, new HashSet<>(Collections.singleton(0)));

        for (char c : input.toCharArray()) {
            Set<Integer> nextStates = new HashSet<>();

            for (int state : currentStates) {
                for (Trans t : nfa.transitions) {
                    if (t.state_from == state && t.trans_symbol == c) {
                        nextStates.add(t.state_to);
                    }
                }
            }

            currentStates = epsilonClosure(nfa, nextStates);

            if (currentStates.isEmpty()) {
                return false; // No valid transition
            }
        }

        return currentStates.contains(nfa.final_state);
    }

    private static Set<Integer> epsilonClosure(NFA nfa, Set<Integer> states) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> closure = new HashSet<>(states);

        for (int state : states) {
            stack.push(state);
        }

        while (!stack.isEmpty()) {
            int state = stack.pop();
            for (Trans t : nfa.transitions) {
                if (t.state_from == state && t.trans_symbol == 'E' && !closure.contains(t.state_to)) {
                    closure.add(t.state_to);
                    stack.push(t.state_to);
                }
            }
        }

        return closure;
    }

    public static void main(String[] args) {
        NFA nfa = new NFA();
        nfa.states.add(0);
        nfa.states.add(1);
        nfa.states.add(2);
        nfa.final_state = 2;
        nfa.transitions.add(new Trans(0, 1, 'E')); // Epsilon transition
        nfa.transitions.add(new Trans(1, 2, 'a')); // Normal transition

        System.out.println(acceptsString(nfa, "a"));  // true
        System.out.println(acceptsString(nfa, "b"));  // false
        System.out.println(acceptsString(nfa, ""));   // false
    }
}

