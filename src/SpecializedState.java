import java.util.ArrayList;

public class SpecializedState {
    public SpecializedState TransitionA;
    public SpecializedState TransitionB;
    public ArrayList<SpecializedState> EpsilonTransitions;

    public SpecializedState(SpecializedState transitionA, SpecializedState transitionB){
        TransitionA = transitionA;
        TransitionB = transitionB;
        EpsilonTransitions = new ArrayList<SpecializedState>();
    }

    public SpecializedState Next(char input){
        if (input == 'a') return TransitionA;
        if (input == 'b') return TransitionB;

        throw new UnsupportedOperationException("Only supports the alphabet {a,b}");
    }
}
