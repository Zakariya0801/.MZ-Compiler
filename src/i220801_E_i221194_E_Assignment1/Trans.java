package i220801_E_i221194_E_Assignment1;

public class Trans{
    public int state_from, state_to;
    public char trans_symbol;

    public Trans(int v1, int v2, char sym){
        this.state_from = v1;
        this.state_to = v2;
        this.trans_symbol = sym;
    }
}