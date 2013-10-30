import java.util.ArrayList;

public class RegexNfa {
    public State Start;
    public State Final;

    public RegexNfa(State start, State finalState) {
        Start = start;
        Final = finalState;
    }

    // TODO: Handle epsilon transitions.
    public boolean Accept(String input) {
        // Handle empty string (e).
        if (input.equals("e")) return Start == Final;

        ArrayList<State> currentStates = new ArrayList<State>();
        currentStates.add(Start);

        for (char c : input.toCharArray()) {
            // Create a new array for the set of states that this input symbol will transition the current states to.
            ArrayList<State> nextStates = new ArrayList<State>();

            // For each current state we take its transition.
            for (State s : currentStates) {
                State next = s.Next(c);

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
