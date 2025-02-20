package i220801_E_i221194_E_Assignment1;
import java.util.*;



public class NFA{
    public ArrayList <Integer> states;
    public ArrayList <Trans> transitions;
    public int final_state;
    
    public NFA(){
        this.states = new ArrayList <Integer> ();
        this.transitions = new ArrayList <Trans> ();
        this.final_state = 0;
    }
    public NFA(int size){
        this.states = new ArrayList <Integer> ();
        this.transitions = new ArrayList <Trans> ();
        this.final_state = 0;
        this.setStateSize(size);
    }
    public NFA(char c){
        this.states = new ArrayList<Integer> ();
        this.transitions = new ArrayList <Trans> ();
        this.setStateSize(2);
        this.final_state = 1;
        this.transitions.add(new Trans(0, 1, c));
    }

    public void setStateSize(int size){
        for (int i = 0; i < size; i++)
            this.states.add(i);
    }

    public void display(){
        for (Trans t: transitions){
            System.out.println("("+ t.state_from +", "+ t.trans_symbol +
                ", "+ t.state_to +")");
        }    
    }
    
 // Add these methods to your NFA class:

    public HashSet<Integer> getEpsilonClosure(int state) {
        HashSet<Integer> closure = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(state);
        closure.add(state);
        
        while (!stack.isEmpty()) {
            int current = stack.pop();
            
            for (Trans t : transitions) {
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

    public DFA toDFA() {
        return NFAtoDFA.convert(this);
    }
 // Add this method to your NFA class
    public DFA toMinimizedDFA() {
        DFA dfa = this.toDFA();
        return DFAMinimizer.minimize(dfa);
    }
}
