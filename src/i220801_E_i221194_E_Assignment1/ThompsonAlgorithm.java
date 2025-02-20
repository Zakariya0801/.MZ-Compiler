package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
public class ThompsonAlgorithm{
   
	public static String expandRange(String input) {
		Pattern pattern = Pattern.compile("\\[(.)-(.)\\]");
        Matcher matcher = pattern.matcher(input);
        
        if (matcher.find()) {
            char start = (matcher.group(1).charAt(0));
            char end = (matcher.group(2).charAt(0));
            
            if (start > end) {
                throw new IllegalArgumentException("Invalid range: start must be less than or equal to end");
            }
            
            StringBuilder expanded = new StringBuilder("(");
            for (char i = start; i <= end; i++) {
                expanded.append(i);
                if (i != end) {
                    expanded.append("|");
                }
            }
            expanded.append(")");
            
            return input.substring(0, matcher.start()-1) +  expanded.toString() + input.substring(matcher.end()+1, input.length());
        }
        return input; // Return input as is if no range is found
    }
	public static List<String> extractParenthesesContent(String input) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\((.*?)\\)\\*?");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group(0); // Get the full matched substring
            match = expandRange(match);
            result.add(match);
        }
        return result;
    }
	public static NFA kleene(NFA n){
	    NFA result = new NFA(n.states.size()+2);
	    result.transitions.add(new Trans(0, 1, 'E')); // new trans for q0
	
	    // copy existing transisitons
	    for (Trans t: n.transitions){
	        result.transitions.add(new Trans(t.state_from + 1,
	        t.state_to + 1, t.trans_symbol));
	    }
	    
	    // add empty transition from final n state to new final state.
	    result.transitions.add(new Trans(n.states.size(), 
	        n.states.size() + 1, 'E'));
	    
	    // Loop back from last state of n to initial state of n.
	    result.transitions.add(new Trans(n.states.size(), 1, 'E'));
	
	    // Add empty transition from new initial state to new final state.
	    result.transitions.add(new Trans(0, n.states.size() + 1, 'E'));
	
	    result.final_state = n.states.size() + 1;
	    return result;
	}
	
	/*
	    concat() - Thompson algorithm for concatenation. Middle Precedence.
	*/
	public static NFA concatCopy(NFA n, NFA m){
	    ///*
	    NFA ans = new NFA();
	    // copy NFA m's transitions to n, and handles connecting n & m
	    int maxi = 0;
	    for (Trans t: n.transitions){
	        ans.transitions.add(new Trans(t.state_from, t.state_to, t.trans_symbol));
	        if(!ans.states.contains(t.state_from)) {
	        	ans.states.add(t.state_from);
	        	if(t.state_from > maxi) maxi = t.state_from;
	        }
	        if(!ans.states.contains(t.state_to)) {
	        	ans.states.add(t.state_to);
	        	if(t.state_to > maxi) maxi = t.state_to;
	        }
	    }
	    maxi++;
	    ans.transitions.add(new Trans(n.final_state,maxi, 'E'));
	    
	    for (Trans t: m.transitions){
	        ans.transitions.add(new Trans(maxi+t.state_from, maxi+t.state_to, t.trans_symbol));
	        if(!ans.states.contains(maxi+t.state_from))
	        	ans.states.add(maxi+t.state_from);
	        if(!ans.states.contains(maxi+t.state_to))
	        	ans.states.add(maxi+t.state_to);
	    }
	    
	    ans.final_state = m.final_state+maxi;
	    return ans;
	    
	}
//	public static NFA concat(NFA n, NFA m){
//	    ///*
//	    m.states.remove(0); // delete m's initial state
//	    
//	    // copy NFA m's transitions to n, and handles connecting n & m
//	    for (Trans t: m.transitions){
//	        n.transitions.add(new Trans(t.state_from + n.states.size()-1,
//	            t.state_to + n.states.size() - 1, t.trans_symbol));
//	    }
//	
//	    // take m and combine to n after erasing inital m state
//	    for (Integer s: m.states){
//	        n.states.add(s + n.states.size() + 1);
//	    }
//	    
//	    n.final_state = n.states.size() + m.states.size() - 2;
//	    return n;
//	}
	
	/*
	    union() - Lowest Precedence regular expression operator. Thompson
	        algorithm for union (or). 
	*/
	public static NFA union(NFA n, NFA m){
	    NFA result = new NFA(n.states.size() + m.states.size() + 2);
	
	    // the branching of q0 to beginning of n
	    result.transitions.add(new Trans(0, 1, 'E'));
	    
	    // copy existing transisitons of n
	    for (Trans t: n.transitions){
	        result.transitions.add(new Trans(t.state_from + 1,
	        t.state_to + 1, t.trans_symbol));
	    }
	    
	    // transition from last n to final state
	    result.transitions.add(new Trans(n.states.size(),
	        n.states.size() + m.states.size() + 1, 'E'));
	
	    // the branching of q0 to beginning of m
	    result.transitions.add(new Trans(0, n.states.size() + 1, 'E'));
	
	    // copy existing transisitons of m
	    for (Trans t: m.transitions){
	        result.transitions.add(new Trans(t.state_from + n.states.size()
	            + 1, t.state_to + n.states.size() + 1, t.trans_symbol));
	    }
	    
	    // transition from last m to final state
	    result.transitions.add(new Trans(m.states.size() + n.states.size(),
	        n.states.size() + m.states.size() + 1, 'E'));
	   
	    // 2 new states and shifted m to avoid repetition of last n & 1st m
	    result.final_state = n.states.size() + m.states.size() + 1;
	    return result;
	}
	
	
	
	// simplify the repeated boolean condition checks
	public static boolean alpha(char c){ return c >= 'a' && c <= 'z';}
	public static boolean numeric(char c){ return c >= '0' && c <= '9';}
	public static boolean alphabet(char c){ return alpha(c) || c == 'E'  || c=='.';}
	public static boolean regexOperator(char c){
	    return c == '(' || c == ')' || c == '*' || c == '|';
	}
	public static boolean validRegExChar(char c){
	    return alphabet(c) || regexOperator(c) || numeric(c) || true;
	}
	// validRegEx() - checks if given string is a valid regular expression.
	public static boolean validRegEx(String regex){
	    if (regex.isEmpty())
	        return false;
	    for (char c: regex.toCharArray())
	        if (!validRegExChar(c))
	            return false;
	    return true;
	}
	
	
	public static NFA compile(String regex){
	    if (!validRegEx(regex)){
	        System.out.println("Invalid Regular Expression Input.");
	        return new NFA(); // empty NFA if invalid regex
	    }
	    
	    Stack <Character> operators = new Stack <Character> ();
	    Stack <NFA> operands = new Stack <NFA> ();
	    Stack <NFA> concat_stack = new Stack <NFA> ();
	    boolean ccflag = false; // concat flag
	    char op, c; // current character of string
	    int para_count = 0;
	    NFA nfa1, nfa2;
	
	    for (int i = 0; i < regex.length(); i++){
	        c = regex.charAt(i);
	        if (!(regexOperator(c))){
	            operands.push(new NFA(c));
	            if (ccflag){ // concat this w/ previous
	                operators.push('.'); 
	            }
	            else
	                ccflag = true;
	        }
	        else{
	            if (c == ')'){
	                ccflag = false;
	                if (para_count == 0){
	                    System.out.println("Error: More end paranthesis "+
	                        "than beginning paranthesis");
	                    System.exit(1);
	                }
	                else{ para_count--;}
	                // process stuff on stack till '('
	                while (!operators.empty() && operators.peek() != '('){
	                    op = operators.pop();
	                    if (op == '.'){
	                        nfa2 = operands.pop();
	                        nfa1 = operands.pop();
	                        operands.push(concatCopy(nfa1, nfa2));
	                    }
	                    else if (op == '|'){
	                        nfa2 = operands.pop();
	                        
	                        if(!operators.empty() && 
	                            operators.peek() == '.'){
	                            
	                            concat_stack.push(operands.pop());
	                            while (!operators.empty() && 
	                                operators.peek() == '.'){
	                                
	                                concat_stack.push(operands.pop());
	                                operators.pop();
	                            }
	                            nfa1 = concatCopy(concat_stack.pop(),
	                                concat_stack.pop());
	                            while (concat_stack.size() > 0){
	                               nfa1 =  concatCopy(nfa1, concat_stack.pop());
	                            }
	                        }
	                        else{
	                            nfa1 = operands.pop();
	                        }
	                        operands.push(union(nfa1, nfa2));
	                    }
	                }
	            }
	            else if (c == '*'){
	                operands.push(kleene(operands.pop()));
	                ccflag = true;
	            }
	            else if (c == '('){ // if any other operator: push
	                operators.push(c);
	                para_count++;
	            }
	            else if (c == '|'){
	                operators.push(c);
	                ccflag = false;
	            }
	        }
	    }
	    while (operators.size() > 0){
	        if (operands.empty()){
	            System.out.println("Error: imbalanace in operands and "
	            + "operators");
	            System.exit(1);
	        }
	        op = operators.pop();
	        if (op == '.'){
	            nfa2 = operands.pop();
	            nfa1 = operands.pop();
	            operands.push(concatCopy(nfa1, nfa2));
	        }
	        else if (op == '|'){
	            nfa2 = operands.pop();
	            if( !operators.empty() && operators.peek() == '.'){
	                concat_stack.push(operands.pop());
	                while (!operators.empty() && operators.peek() == '.'){
	                    concat_stack.push(operands.pop());
	                    operators.pop();
	                }
	                nfa1 = concatCopy(concat_stack.pop(),
	                    concat_stack.pop());
	                while (concat_stack.size() > 0){
	                   nfa1 =  concatCopy(nfa1, concat_stack.pop());
	                }
	            }
	            else{
	                nfa1 = operands.pop();
	            }
	            operands.push(union(nfa1, nfa2));
	        }
	    }
	    return operands.pop();
	}

}