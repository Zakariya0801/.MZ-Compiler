package i220801_E_i221194_E_Assignment1;

import java.util.Objects;

class State {
    private String name;
    private boolean isAccepting;

    public State(String name, boolean isAccepting) {
        this.name = name;
        this.isAccepting = isAccepting;
    }

    public String getName() {
        return name;
    }

    public boolean isAccepting() {
        return isAccepting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return name.equals(state.name);
    }

    
}