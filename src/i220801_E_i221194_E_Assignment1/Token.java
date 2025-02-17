package i220801_E_i221194_E_Assignment1;

import java.util.ArrayList;
import java.util.List;

public class Token {
	private TokenType type;
    private String value;
    private static List<String> dataTypes = null;
    public Token(TokenType type, String value) {
    	if(dataTypes == null) {
    		dataTypes = new ArrayList<>();
    		String[] str = {
    				"integer", 
		    		"character",
		    		"void",
		    		"decimal"
    		};
    		for(int i=0 ; i<str.length ; i++) 
    			dataTypes.add(str[i]);
    	}
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
    
    public boolean isDatatype() {
    	if(this.type != TokenType.KEYWORD) return false;
    	if(dataTypes.contains(value)) return true;
    	return false;
    }
    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}
