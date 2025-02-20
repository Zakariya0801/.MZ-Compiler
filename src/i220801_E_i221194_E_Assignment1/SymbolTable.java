package i220801_E_i221194_E_Assignment1;

import java.util.HashSet;
import java.util.Set;

public class SymbolTable {
    Set<Token> tokens;

    public SymbolTable() {
        tokens = new HashSet<>();
    }

    void addToken(Token t) {
        tokens.add(t);
    }

    void printTable() {
        if (tokens.isEmpty()) {
            System.out.println("Symbol Table is empty.");
            return;
        }

        // Printing header
        System.out.println("+-----------------+--------------------------------+");
        System.out.printf("| %-15s | %-30s |%n", "Type", "Value");
        System.out.println("+-----------------+--------------------------------+");

        // Printing tokens
        for (Token token : tokens) {
        	if(token.getType() != TokenType.UNKNOWN)
        		System.out.printf("| %-15s | %-30s |%n", token.getType(), token.getValue());
        }

        // Footer line
        System.out.println("+-----------------+--------------------------------+");
    }
}

