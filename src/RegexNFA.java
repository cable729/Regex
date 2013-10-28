import java.util.ArrayList;

public class RegexNfa {
    public SpecializedState Start;
    public SpecializedState Final;

    public RegexNfa(SpecializedState start, SpecializedState finalState) {
        Start = start;
        Final = finalState;
    }

    // TODO: Handle epsilon transitions.
    public boolean Accept(String input) {
        // Handle empty string (e).
        if (input.equals("e")) return Start == Final;

        ArrayList<SpecializedState> currentStates = new ArrayList<SpecializedState>();
        currentStates.add(Start);

        for (char c : input.toCharArray()) {
            // Create a new array for the set of states that this input symbol will transition the current states to.
            ArrayList<SpecializedState> nextStates = new ArrayList<SpecializedState>();

            // For each current state we take its transition.
            for (SpecializedState s : currentStates) {
                SpecializedState next = s.Next(c);

                // If it's not null then there is a matching transition. We only add distinct states
                // because we're mimicking a mathematical set object.
                if (next != null && !nextStates.contains(next)) {
                    nextStates.add(next);
                }
            }
            currentStates = nextStates;
        }

        return currentStates.contains(Final);
    }
}
