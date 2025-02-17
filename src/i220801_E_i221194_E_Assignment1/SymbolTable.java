package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable {
	
	HashMap<TokenType,List<String>> tokens;
	List<TokenType> tokenTypes;
	public SymbolTable() {
		tokens = new HashMap<>();
		tokenTypes = List.of(TokenType.values());
		for(TokenType t: tokenTypes) 
			tokens.put(t, new ArrayList<>());	
	}
	public boolean addtoSymbolTable(Token t) {
		if(tokens.get(t.getType()).contains(t.getValue())) return false;
		tokens.get(t.getType()).add(t.getValue());
		return true;
	}
	public void printSymbolTable() {
        String header1 = "Token Type";
        String header2 = "Values";

        // Determine the max width dynamically
        int maxTypeLength = header1.length();
        int maxValueLength = header2.length();

        for (TokenType type : tokens.keySet()) {
            maxTypeLength = Math.max(maxTypeLength, type.toString().length());
            String values = String.join(", ", tokens.get(type));
            maxValueLength = Math.max(maxValueLength, values.length());
        }

        // Padding for readability
        maxTypeLength += 2;
        maxValueLength += 2;

        // Table width
        int totalWidth = maxTypeLength + maxValueLength + 7;

        // Print the top border
        System.out.println("+" + "-".repeat(totalWidth - 2) + "+");

        // Print header row
        System.out.printf("| %-" + maxTypeLength + "s | %-" + maxValueLength + "s |\n", header1, header2);

        // Print separator
        System.out.println("+" + "-".repeat(totalWidth - 2) + "+");

        // Print table rows
        for (TokenType type : tokens.keySet()) {
            String values = String.join(" ", tokens.get(type));
            System.out.printf("| %-" + maxTypeLength + "s | %-" + maxValueLength + "s |\n", type, values);
        }

        // Print the bottom border
        System.out.println("+" + "-".repeat(totalWidth - 2) + "+");
    }
	
}
