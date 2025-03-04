package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;

public class Token {
	private TokenType type;
    private String value;
    public Token(TokenType type, String value) {
    
        this.type = type;
        this.value = value;
    }
    
    public void setType(TokenType type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TokenType getType() {
        return type;
    }
	
    public String getValue() {
        return value;
    }
    
   
    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}
