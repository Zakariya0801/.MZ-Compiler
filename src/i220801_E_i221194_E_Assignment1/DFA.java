package i220801_E_i221194_E_Assignment1;
import java.util.*;


class DFA {
    private State startState;
    private Set<State> states;
    private Set<Character> alphabet;
    private Map<State, Map<Character, State>> transitions;
    
    public DFA(State startState) {
        this.startState = startState;
        this.states = new HashSet<>();
        this.states.add(startState);
        this.alphabet = new HashSet<>();
        this.transitions = new HashMap<>();
        this.transitions.put(startState, new HashMap<>());
    }
    
    public void addState(State state) {
        states.add(state);
        if (!transitions.containsKey(state)) {
            transitions.put(state, new HashMap<>());
        }
    }
    
    public void addTransition(State from, char symbol, State to) {
        if (!states.contains(from) || !states.contains(to)) {
            throw new IllegalArgumentException("States must be added to DFA first");
        }
        alphabet.add(symbol);
        transitions.get(from).put(symbol, to);
    }
    
    public boolean accepts(String input) {
        State currentState = startState;
        
        for (char c : input.toCharArray()) {
            if (!alphabet.contains(c)) {
                return false;  // Invalid symbol
            }
            
            Map<Character, State> stateTransitions = transitions.get(currentState);
            if (!stateTransitions.containsKey(c)) {
                return false;  // No transition defined
            }
            
            currentState = stateTransitions.get(c);
        }
        
        return currentState.isAccepting();
    }
    
    public State getStartState() {
        return startState;
    }
    
    public Set<State> getStates() {
        return Collections.unmodifiableSet(states);
    }
    
    public Set<Character> getAlphabet() {
        return Collections.unmodifiableSet(alphabet);
    }
    
    public void printTransitionTable() {
        // Get sorted list of states and alphabet for consistent output
        List<State> sortedStates = new ArrayList<>(states);
        sortedStates.sort(Comparator.comparing(State::getName));
        List<Character> sortedAlphabet = new ArrayList<>(alphabet);
        Collections.sort(sortedAlphabet);

        // Calculate column widths
        int stateColWidth = Math.max(10, 
            states.stream()
                  .map(s -> s.getName().length())
                  .max(Comparator.naturalOrder())
                  .orElse(10));

        // Print header
        System.out.println("\nTransition Table:");
        System.out.println("(\" * indicates accepting state)");
        
        // Print alphabet header
        System.out.printf("%-" + stateColWidth + "s |", "State");
        for (char symbol : sortedAlphabet) {
            System.out.printf(" %-" + stateColWidth + "c |", symbol);
        }
        System.out.println();

        // Print separator
        String separator = "-".repeat(stateColWidth) + "+";
        separator = separator + ("-".repeat(stateColWidth + 2) + "+").repeat(sortedAlphabet.size());
        System.out.println(separator);

        // Print transitions for each state
        for (State state : sortedStates) {
            // Add asterisk for accepting states
            String stateName = state.getName() + (state.isAccepting() ? "*" : "");
            System.out.printf("%-" + stateColWidth + "s |", stateName);

            // Print transition for each input symbol
            for (char symbol : sortedAlphabet) {
                State nextState = transitions.get(state).get(symbol);
                String transitionStr = nextState != null ? nextState.getName() : "-";
                System.out.printf(" %-" + stateColWidth + "s |", transitionStr);
            }
            System.out.println();
        }
        System.out.println();
    }
}
