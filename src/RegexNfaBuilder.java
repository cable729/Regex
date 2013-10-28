public class RegexNfaBuilder {

    private final String regex;
    private RegexNfa nfa;

    public RegexNfaBuilder(String regex) {
        this.regex = regex;
    }

    private void handleA() {
        if (nfa.Final.TransitionA != null) {
            nfa.Final = nfa.Final.TransitionA;
        } else {
            SpecializedState next = new SpecializedState(null, null);
            nfa.Final.TransitionA = next;
            nfa.Final = next;
        }
    }

    private void handleB() {
        if (nfa.Final.TransitionB != null) {
            nfa.Final = nfa.Final.TransitionB;
        } else {
            SpecializedState next = new SpecializedState(null, null);
            nfa.Final.TransitionB = next;
            nfa.Final = next;
        }
    }

    private void handleUnion(int position) {
        if (regex.length() == position + 1) {
            throw new IllegalArgumentException("Regex must not have | as the last character.");
        }

        String rightRegexOperand = ParserHelper.StringBeforeSameLevelUnion(regex, position);
        RegexNfa rightNFA = build(rightRegexOperand, nfa.Start);

        rightNFA.Final.EpsilonTransitions.add(nfa.Final);
    }

    private RegexNfa build(String regex, SpecializedState root) {
        nfa = new RegexNfa(root, root);

        for (int i = 0; i < regex.length(); i++) {
            switch (regex.charAt(i)) {
                case 'a':
                    handleA();
                    break;
                case 'b':
                    handleB();
                    break;
                case '|':
                    handleUnion(i);
                    break;

            }
        }

        return nfa;
    }

    public RegexNfa Build(String regex) {
        // Our starting nfa contains one state that has no transitions and is the start and final state.
        SpecializedState start = new SpecializedState(null, null);
        return build(regex, start);
    }
}
