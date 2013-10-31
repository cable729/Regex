import java.util.HashSet;
import java.util.Set;

public class State {
    public State TransitionA;
    public State TransitionB;
    public Set<State> EpsilonTransitions;

    public State() {
        EpsilonTransitions = new HashSet<State>();
    }

    public Set<State> EpsilonClosure() {
        Set<State> closure = new HashSet<State>(EpsilonTransitions);

        for (State s : EpsilonTransitions) {
            closure.addAll(s.EpsilonClosure());
        }

        return closure;
    }

    public Set<State> Next(char input) {
        Set<State> nextStates = new HashSet<State>();
        if (TransitionA != null && TransitionB != null) {
            nextStates.addAll(EpsilonClosure());
        }

        if (input == 'a' && TransitionA != null) {
            nextStates.add(TransitionA);
            nextStates.addAll(TransitionA.EpsilonClosure());
        }
        if (input == 'b' && TransitionB != null) {
            nextStates.add(TransitionB);
            nextStates.addAll(TransitionB.EpsilonClosure());
        }

        return nextStates;
    }
}
