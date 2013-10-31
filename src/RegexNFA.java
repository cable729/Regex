import java.util.HashSet;
import java.util.Set;

public class RegexNfa {
    public State Start;
    public State Final;

    public RegexNfa(State start, State finalState) {
        Start = start;
        Final = finalState;
    }

    public boolean Accept(String input) {
        input = input.replace("e", "");

        Set<State> currentStates = new HashSet<State>();
        currentStates.add(Start);
        currentStates.addAll(Start.EpsilonClosure());

        for (char c : input.toCharArray()) {
            // Create a new array for the set of states that this input symbol will transition the current states to.
            Set<State> nextStates = new HashSet<State>();

            // For each current state we take its transition.
            for (State s : currentStates) {
                nextStates.addAll(s.Next(c));
            }
            // Remove current states and add the new states.
            currentStates.clear();
            currentStates.addAll(nextStates);
        }

        return currentStates.contains(Final);
    }
}
