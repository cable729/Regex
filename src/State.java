import java.util.HashSet;
import java.util.Set;

public class State {
    public State TransitionA;
    public State TransitionB;
    public Set<State> EpsilonTransitions;

    public State() {
        EpsilonTransitions = new HashSet<State>();
    }

    private Set<State> EpsilonClosure() {
        Set<State> closure = new HashSet<State>(EpsilonTransitions);

        for (State s : EpsilonTransitions) {
            closure.addAll(s.EpsilonClosure());
        }

        return closure;
    }

    public Set<State> Next(char input) {
        Set<State> nextStates = EpsilonClosure();

        if (input == 'a') nextStates.add(TransitionA);
        if (input == 'b') nextStates.add(TransitionB);

        return nextStates;
    }
}
