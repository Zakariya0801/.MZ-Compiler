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
}
