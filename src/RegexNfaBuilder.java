public class RegexNfaBuilder {
    private static RegexNfa FromRegexString(String regex, SpecializedState root) {

        RegexNfa nfa = new RegexNfa(root, root);

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
                RegexNfa rightNFA = FromRegexString(rightRegexOperand, nfa.Start);

                rightNFA.Final.EpsilonTransitions.add(nfa.Final);
            } else if (regArray[i] == '(') {

            } else if (regArray[i] == '*') {

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

    public static RegexNfa FromRegexString(String regex) {

        // Our starting nfa contains one state that has no transitions and is the start and final state.
        SpecializedState start = new SpecializedState(null, null);
        return FromRegexString(regex, start);
    }
}
