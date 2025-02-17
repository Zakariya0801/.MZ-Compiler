package i220801_E_i221194_E_Assignment1;
import java.util.*;


class DFA {
    private State startState;
    private Set<State> states;
    private Set<String> alphabet;
    private Map<State, Map<String, State>> transitions;
    private Map<State, State> defaultTransitions; // New: for handling "else" cases

    public DFA(State startState) {
        this.startState = startState;
        this.states = new HashSet<>();
        this.states.add(startState);
        this.alphabet = new HashSet<>();
        this.transitions = new HashMap<>();
        this.transitions.put(startState, new HashMap<>());
        this.defaultTransitions = new HashMap<>(); // Initialize default transitions map
    }
    
    public void addState(State state) {
        states.add(state);
        if (!transitions.containsKey(state)) {
            transitions.put(state, new HashMap<>());
        }
    }
    
    // New method to add default transition
    public void addDefaultTransition(State from, State to) {
        if (!states.contains(from) || !states.contains(to)) {
            throw new IllegalArgumentException("States must be added to DFA first");
        }
        defaultTransitions.put(from, to);
    }
    
    public void addTransition(State from, String symbol, State to) {
        if (!states.contains(from) || !states.contains(to)) {
            throw new IllegalArgumentException("States must be added to DFA first");
        }
        alphabet.add(symbol);
        transitions.get(from).put(symbol, to);
    }
    
    public boolean accepts(String input) {
        State currentState = startState;
        
        for (char c : input.toCharArray()) {
            Map<String, State> stateTransitions = transitions.get(currentState);
            State nextState = stateTransitions.get(c);
            
            if (nextState == null) {
                // If no explicit transition, try default transition
                nextState = defaultTransitions.get(currentState);
                if (nextState == null) {
                    return false; // No transition found
                }
            }
            
            currentState = nextState;
        }
        
        return currentState.isAccepting();
    }

    // Modified createFromRules to handle else transitions
   
    
    public void printTransitionTable() {
        // Get sorted list of states and alphabet for consistent output
        List<State> sortedStates = new ArrayList<>(states);
        sortedStates.sort(Comparator.comparing(State::getName));
        List<String> sortedAlphabet = new ArrayList<>(alphabet);
        Collections.sort(sortedAlphabet);

        // Calculate column widths
        int stateColWidth = Math.max(10,
            Math.max(
                states.stream()
                    .map(s -> s.getName().length())
                    .max(Comparator.naturalOrder())
                    .orElse(10),
                "any".length()
            )
        );

        // Print header
        System.out.println("\nTransition Table:");
        System.out.println("(* indicates accepting state)");

        // Print alphabet header
        System.out.printf("%-" + stateColWidth + "s |", "State");
        for (String symbol : sortedAlphabet) {
            System.out.printf(" %-" + stateColWidth + "s |", symbol);
        }
        // Add else column
        System.out.printf(" %-" + stateColWidth + "s |", "any");
        System.out.println();

        // Print separator
        String separator = "-".repeat(stateColWidth) + "+";
        separator = separator + ("-".repeat(stateColWidth + 2) + "+").repeat(sortedAlphabet.size() + 1); // +1 for else column
        System.out.println(separator);

        // Print transitions for each state
        for (State state : sortedStates) {
            // Add asterisk for accepting states
            String stateName = state.getName() + (state.isAccepting() ? "*" : "");
            System.out.printf("%-" + stateColWidth + "s |", stateName);

            // Print transition for each input symbol
            for (String symbol : sortedAlphabet) {
                State nextState = transitions.get(state).get(symbol);
                String transitionStr = nextState != null ? nextState.getName() : "-";
                System.out.printf(" %-" + stateColWidth + "s |", transitionStr);
            }

            // Print default transition if it exists
            State defaultState = defaultTransitions.get(state);
            String defaultTransitionStr = defaultState != null ? defaultState.getName() : "-";
            System.out.printf(" %-" + stateColWidth + "s |", defaultTransitionStr);
            
            System.out.println();
        }

//        // Print additional information about default transitions
//        if (!defaultTransitions.isEmpty()) {
//            System.out.println("\nDefault Transitions:");
//            System.out.println("These transitions are taken when no other transition matches the input symbol.");
//            for (Map.Entry<State, State> entry : defaultTransitions.entrySet()) {
//                System.out.printf("From %s: any unspecified input → %s%n", 
//                    entry.getKey().getName(), 
//                    entry.getValue().getName());
//            }
//        }
//        
        System.out.println();
    }
}
