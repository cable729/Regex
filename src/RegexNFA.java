import java.util.ArrayList;

public class RegexNFA {
    public SpecializedState Start;
    public SpecializedState Final;

    public RegexNFA(SpecializedState start, SpecializedState finalState) {
        Start = start;
        Final = finalState;
    }

    private static RegexNFA FromRegexString(String regex, SpecializedState root) {

        RegexNFA nfa = new RegexNFA(root, root);

        char[] regArray = regex.toCharArray();
        for (int i = 0; i < regArray.length; i++) {

            if (regArray[i] == 'a') {
                if (nfa.Final.TransitionA != null) {
                    nfa.Final = nfa.Final.TransitionA;
                } else {
                    SpecializedState next = new SpecializedState(null, null);
                    nfa.Final.TransitionA = next;
                    nfa.Final = next;
                }

            } else if (regArray[i] == 'b') {
                if (nfa.Final.TransitionB != null) {
                    nfa.Final = nfa.Final.TransitionB;
                } else {
                    SpecializedState next = new SpecializedState(null, null);
                    nfa.Final.TransitionB = next;
                    nfa.Final = next;
                }

            } else if (regArray[i] == '|') {
                if (regArray.length == i + 1) {
                    throw new IllegalArgumentException("Regex must not have | as the last character.");
                }

                String rightRegexOperand = ParserHelper.StringBeforeSameLevelUnion(regex, i);
                RegexNFA rightNFA = FromRegexString(rightRegexOperand, nfa.Start);

                rightNFA.Final.EpsilonTransitions.add(nfa.Final);
            }

//                // We only consider an opening parentheses because we only retrieve the string inside
//                // the parentheses. We then jump to the point after the closing parentheses.
//                case '(':
//                    String inner = ParserHelper.StringInsideParentheses(regex, i);
//
//                    // This builds a new NFA from everything inside the parentheses. The starting state
//                    // of the new NFA is the Final state of the original NFA. We will keep the inner NFA
//                    // in case the next character is a union (|) or repetition (*) character.
//                    leftNFA = FromRegexString(inner, nfa.Final);
//
//                    // Here we merge the two NFAs.
//                    nfa.Final = leftNFA.Final;
//
//                    // Now we must move the index to the end of the closing parenthesis because we recursively
//                    // read it. We move 1 + inner.length, ending up at the closing parentheses. The for loop will
//                    // move us past it.
//                    i += 1 + inner.length();
//
//                    break;
        }

        return nfa;
    }

    public static RegexNFA FromRegexString(String regex) {

        // Our starting nfa contains one state that has no transitions and is the start and final state.
        SpecializedState start = new SpecializedState(null, null);
        return FromRegexString(regex, start);
    }

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

    public void MakeRepeatable() {
        Final.TransitionA = Start.TransitionA;
        Final.TransitionB = Start.TransitionB;
        Start = Final;
    }
}
