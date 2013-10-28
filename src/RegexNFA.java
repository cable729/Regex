import java.util.ArrayList;

public class RegexNFA {
    public SpecializedState Start;
    public SpecializedState Final;

    public RegexNFA(SpecializedState start, SpecializedState finalState){
        Start = start;
        Final = finalState;
    }

    public boolean Accept(String input){
        // Handle empty string (e).
        if (input.equals("e")) return Start == Final;

        ArrayList<SpecializedState> currentStates = new ArrayList<SpecializedState>();
        currentStates.add(Start);

        for (char c : input.toCharArray()){
            // Create a new array for the set of states that this input symbol will transition the current states to.
            ArrayList<SpecializedState> nextStates = new ArrayList<SpecializedState>();

            // For each current state we take its transition.
            for (SpecializedState s : currentStates) {
                SpecializedState next = s.Next(c);

                // If it's not null then there is a matching transition. We only add distinct states
                // because we're mimicking a mathematical set object.
                if (next != null && !nextStates.contains(next)){
                    nextStates.add(next);
                }
            }
            currentStates = nextStates;
        }

        return currentStates.contains(Final);
    }

    public void MakeRepeatable(){
        Final.TransitionA = Start.TransitionA;
        Final.TransitionB = Start.TransitionB;
        Start = Final;
    }

    private static RegexNFA FromRegexString(String regex, SpecializedState root){

        RegexNFA nfa = new RegexNFA(root, root);
        RegexNFA leftNFA = null;

        char[] regArray = regex.toCharArray();
        for (int i = 0; i < regArray.length; i++){

            switch (regArray[i]){

                // We only consider an opening parentheses because we only retrieve the string inside
                // the parentheses. We then jump to the point after the closing parentheses.
                case '(':
                    String inner = ParserHelper.StringInsideParentheses(regex, i);

                    // This builds a new NFA from everything inside the parentheses. The starting state
                    // of the new NFA is the Final state of the original NFA. We will keep the inner NFA
                    // in case the next character is a union (|) or repetition (*) character.
                    leftNFA = FromRegexString(inner, nfa.Final);

                    // Here we merge the two NFAs.
                    nfa.Final = leftNFA.Final;

                    // Now we must move the index to the end of the closing parenthesis because we recursively
                    // read it. We move 1 + inner.length, ending up at the closing parentheses. The for loop will
                    // move us past it.
                    i += 1 + inner.length();

                    break;

                case '*':
                    // Case: leftNFA == null ???
                    leftNFA.MakeRepeatable();
                    nfa.Final = leftNFA.Final;

                    break;

                case '|':
                    // Since union has the lowest precedence, we connect two NFAs to one end state:
                    //     1) the left side of the union, held in leftNFA
                    //     2) the right side of the union, retrieved in the next function
                    // An operand of a union is only "cut off" at another same-level (parentheses-wise) union.
                    String rightUnion = ParserHelper.StringBeforeSameLevelUnion(regex, i);

                    RegexNFA rightNFA = FromRegexString(rightUnion, leftNFA.Start);

                    break;

                // Covers the alphabet. If the character is not in the alphabet, we have an invalid input.
                default:
                    // Set leftNFA here <<<


                    break;
            }
        }

        return nfa;
    }
    public static RegexNFA FromRegexString(String regex){

        // Our starting nfa contains one state that has no transitions and is the start and final state.
        SpecializedState start = new SpecializedState(null, null);
        return FromRegexString(regex, start);
    }
}
