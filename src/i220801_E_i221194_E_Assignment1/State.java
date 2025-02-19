package i220801_E_i221194_E_Assignment1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class State {
    int id;
    Map<Character, List<State>> transitions = new HashMap<>();
    boolean isFinal = false;

    State(int id) {
        this.id = id;
    }
}
