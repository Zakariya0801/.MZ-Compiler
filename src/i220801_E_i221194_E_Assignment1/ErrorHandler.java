package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
	private static List<Token> errors = new ArrayList<>();
	
	private ErrorHandler() {
		
	}
	public static void addError(Token t) {
		errors.add(t);
	}
	
	public static void printTable() {
        if (errors.isEmpty()) {
            System.out.println("There are no Lexical Errors");
            return;
        }

        // Printing header
        System.out.println("+------------+--------------------------------+");
        System.out.printf("| %-10s | %-30s |%n", "Type", "Value");
        System.out.println("+------------+--------------------------------+");

        // Printing tokens
        for (Token token : errors) {
        	
        	System.out.printf("| %-10s | %-30s |%n", token.getType(), token.getValue());
        }

        // Footer line
        System.out.println("+------------+--------------------------------+");
    }
}
