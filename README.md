# Lexical Analyzer in Java

## Overview
This project implements a **Lexical Analyzer** in Java. The program processes **Regular Expressions (REs)** for all lexemes and converts them into **Non-Deterministic Finite Automaton (NFA)**, which is further transformed into a **Minimized Deterministic Finite Automaton (DFA)**. The lexical analyzer then reads input code, tokenizes it based on spaces or newline characters (`\r\n`), and determines the appropriate token category by matching it against the RE-defined DFA. If a token does not match any RE, it is sent to the **Error Analyzer**.

## Features
- **Regular Expression Parsing:** Accepts REs for lexemes and processes them into an NFA.
- **NFA to DFA Conversion:** Converts NFA into a DFA and minimizes it for efficiency.
- **Lexical Analysis:** Tokenizes the input code into individual words based on whitespace and newlines.
- **Symbol Table Management:** Maintains a symbol table where recognized lexemes are stored.
- **Error Handling:** Unmatched tokens are forwarded to the error analyzer for reporting.

## Workflow
1. **Input Regular Expressions (REs):**
   - User provides REs for all required lexemes.
   - The program constructs an **NFA** from these REs.
   - NFA is converted into a **Minimized DFA**.
   
2. **Lexical Analysis:**
   - The input code is broken into individual tokens using whitespace (` `) or newlines (`\r\n`).
   - Each token is compared against the **DFA** to find a matching RE.
   
3. **Token Classification:**
   - If a token matches a RE, it is added to the **Symbol Table**.
   - If no match is found, the token is sent to the **Error Analyzer**.

## Installation & Usage
### Prerequisites
- Java (JDK 8 or later)
- Any Java-compatible IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Example
### **Input REs:**
```text
[0-9]+   -> INTEGER
[a-zA-Z_][a-zA-Z0-9_]*  -> IDENTIFIER
"(\\.|[^\\"])*"  -> STRING_LITERAL
\+|-|\*|/   -> OPERATOR
```

### **Sample Input Code:**
```text
int x = 10;
string name = "Lexical Analyzer";
if (x > 5) {
    x = x + 1;
}
```

### **Generated Tokens:**
| Token | Type |
|--------|------------|
| `integer` | Keyword |
| `x` | Identifier |
| `=` | Operator |
| `10` | Integer |
| `;` | Separator |
| `string` | Keyword |
| `name` | Identifier |
| `=` | Operator |
| `"Lexical Analyzer"` | String Literal |

### **Error Handling Example:**
If the input contains an invalid token (e.g., `@symbol`), the program will report an error:
```
Error: Unrecognized token '@symbol' at line 3.
```

## File Structure
```
lexical-analyzer-java/
│── src/
│   ├── Files
│── README.md                 # Project Documentation
```

---

### **Author:**
Your Name (@Zakariya0801)
