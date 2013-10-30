import java.util.ArrayList;

public class State {
    public State TransitionA;
    public State TransitionB;
    public ArrayList<State> EpsilonTransitions;

    public State() {
        EpsilonTransitions = new ArrayList<State>();
    }

    public State Next(char input) {
        if (input == 'a') return TransitionA;
        if (input == 'b') return TransitionB;

        throw new UnsupportedOperationException("Only supports the alphabet {a,b}");
    }
}
