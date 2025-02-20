package i220801_E_i221194_E_Assignment1;
import java.util.*;

public class DFAMinimizer {
    
    public static DFA minimize(DFA dfa) {
        // Step 1: Remove unreachable states
        DFA trimmedDFA = removeUnreachableStates(dfa);
        // Step 2: Partition states into distinguishable groups
        ArrayList<HashSet<Integer>> partitions = partitionStates(trimmedDFA);
        // Step 3: Build the minimized DFA
        return buildMinimizedDFA(trimmedDFA, partitions);
    }
    
    private static DFA removeUnreachableStates(DFA dfa) {
        HashSet<Integer> reachableStates = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        // Start from initial state
        queue.add(dfa.initial_state);
        reachableStates.add(dfa.initial_state);
        
        // BFS to find all reachable states
        while (!queue.isEmpty()) {
            int currentState = queue.poll();
            
            for (Trans t : dfa.transitions) {
                if (t.state_from == currentState && !reachableStates.contains(t.state_to)) {
                    reachableStates.add(t.state_to);
                    queue.add(t.state_to);
                }
            }
        }
        
        // Create new DFA with only reachable states
        DFA trimmedDFA = new DFA();
        trimmedDFA.alphabet = new HashSet<>(dfa.alphabet);
        trimmedDFA.initial_state = dfa.initial_state;
        
        // Map old states to new indices
        HashMap<Integer, Integer> stateMap = new HashMap<>();
        int newIndex = 0;
        
        for (int state : dfa.states) {
            if (reachableStates.contains(state)) {
                stateMap.put(state, newIndex);
                trimmedDFA.states.add(newIndex);
                
                if (dfa.final_states.contains(state)) {
                    trimmedDFA.final_states.add(newIndex);
                }
                
                newIndex++;
            }
        }
        
        // Add transitions for reachable states
        for (Trans t : dfa.transitions) {
            if (reachableStates.contains(t.state_from) && reachableStates.contains(t.state_to)) {
                trimmedDFA.transitions.add(new Trans(
                    stateMap.get(t.state_from),
                    stateMap.get(t.state_to),
                    t.trans_symbol
                ));
            }
        }
        
        return trimmedDFA;
    }
    
    private static ArrayList<HashSet<Integer>> partitionStates(DFA dfa) {
        // Initial partition: final states and non-final states
        ArrayList<HashSet<Integer>> partitions = new ArrayList<>();
        HashSet<Integer> finalStates = new HashSet<>(dfa.final_states);
        HashSet<Integer> nonFinalStates = new HashSet<>();
        
        for (int state : dfa.states) {
            if (!finalStates.contains(state)) {
                nonFinalStates.add(state);
            }
        }
        
        // Add both partitions if they're not empty
        if (!finalStates.isEmpty()) {
            partitions.add(finalStates);
        }
        if (!nonFinalStates.isEmpty()) {
            partitions.add(nonFinalStates);
        }
        
        boolean changed;
        do {
            changed = false;
            ArrayList<HashSet<Integer>> newPartitions = new ArrayList<>();
            
            // For each existing partition
            for (HashSet<Integer> partition : partitions) {
                if (partition.size() <= 1) {
                    newPartitions.add(partition);
                    continue;
                }
                
                // Try to split this partition
                ArrayList<HashSet<Integer>> splitResult = splitPartition(partition, partitions, dfa);
                
                if (splitResult.size() > 1) {
                    changed = true;
                    newPartitions.addAll(splitResult);
                } else {
                    newPartitions.add(partition);
                }
            }
            
            partitions = newPartitions;
        } while (changed);
        
        return partitions;
    }
    
    private static ArrayList<HashSet<Integer>> splitPartition(
            HashSet<Integer> partition, 
            ArrayList<HashSet<Integer>> allPartitions, 
            DFA dfa) {
        
        // If partition has only one state, it can't be split further
        if (partition.size() <= 1) {
            ArrayList<HashSet<Integer>> result = new ArrayList<>();
            result.add(partition);
            return result;
        }
        
        // For each character in alphabet
        for (char symbol : dfa.alphabet) {
            // Map from partition index to states that go to that partition
            HashMap<Integer, HashSet<Integer>> transitionMap = new HashMap<>();
            
            // For each state in the current partition
            for (int state : partition) {
                // Find where this state goes on the current symbol
                int targetState = -1;
                
                for (Trans t : dfa.transitions) {
                    if (t.state_from == state && t.trans_symbol == symbol) {
                        targetState = t.state_to;
                        break;
                    }
                }
                
                // Find which partition contains the target state
                int targetPartitionIndex = -1;
                for (int i = 0; i < allPartitions.size(); i++) {
                    if (allPartitions.get(i).contains(targetState)) {
                        targetPartitionIndex = i;
                        break;
                    }
                }
                
                // Group states by their target partition
                if (!transitionMap.containsKey(targetPartitionIndex)) {
                    transitionMap.put(targetPartitionIndex, new HashSet<>());
                }
                transitionMap.get(targetPartitionIndex).add(state);
            }
            
            // If we found a way to split the partition
            if (transitionMap.size() > 1) {
                return new ArrayList<>(transitionMap.values());
            }
        }
        
        // Couldn't split this partition
        ArrayList<HashSet<Integer>> result = new ArrayList<>();
        result.add(partition);
        return result;
    }
    
    private static DFA buildMinimizedDFA(DFA dfa, ArrayList<HashSet<Integer>> partitions) {
        DFA minimizedDFA = new DFA();
        minimizedDFA.alphabet = new HashSet<>(dfa.alphabet);
        
        // Map from old state to partition index
        HashMap<Integer, Integer> stateToPartition = new HashMap<>();
        for (int i = 0; i < partitions.size(); i++) {
            for (int state : partitions.get(i)) {
                stateToPartition.put(state, i);
            }
        }
        
        // Set up states in minimized DFA
        for (int i = 0; i < partitions.size(); i++) {
            minimizedDFA.states.add(i);
        }
        // Set initial state
        minimizedDFA.initial_state = stateToPartition.get(dfa.initial_state);
        
        // Set final states
        for (int finalState : dfa.final_states) {
            minimizedDFA.final_states.add(stateToPartition.get(finalState));
        }
        
        // Add transitions
        HashSet<String> addedTransitions = new HashSet<>(); // To avoid duplicates
        
        for (Trans t : dfa.transitions) {
            int fromPartition = stateToPartition.get(t.state_from);
            int toPartition = stateToPartition.get(t.state_to);
            
            String transKey = fromPartition + "," + t.trans_symbol + "," + toPartition;
            if (!addedTransitions.contains(transKey)) {
                minimizedDFA.transitions.add(new Trans(fromPartition, toPartition, t.trans_symbol));
                addedTransitions.add(transKey);
            }
        }
        
        return minimizedDFA;
    }
}