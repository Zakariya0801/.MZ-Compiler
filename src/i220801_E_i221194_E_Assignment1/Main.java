package i220801_E_i221194_E_Assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		String regex = "(a|b)*";
        NFA nfa = ThompsonAlgorithm.compile(regex);
        System.out.println("Conversion Complete");
        nfa.display();
        System.out.println(nfa.final_state);
	}

}
