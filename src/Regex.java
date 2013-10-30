public class Regex {

    public static RegexNfa Union(RegexNfa left, RegexNfa right) {
        // Create a new start and end state. The start state epsilons to the left
        // and right start states. Then epsilon left and right final states to new
        // final state.

        RegexNfa union = new RegexNfa(new State(), new State());

        union.Start.EpsilonTransitions.add(left.Start);
        union.Start.EpsilonTransitions.add(right.Start);

        left.Final.EpsilonTransitions.add(union.Final);
        right.Final.EpsilonTransitions.add(union.Final);

        return union;
    }

    public static RegexNfa Star(RegexNfa nfa) {
        // Transition nfa final to start and change final to start

        nfa.Final.EpsilonTransitions.add(nfa.Start);
        nfa.Final = nfa.Start;

        return nfa;
    }

    public static RegexNfa Concatenate(RegexNfa left, RegexNfa right) {
        // Create a new nfa that starts at left.start and ends at right.final.
        // Transition on epsilon between left.final and right.start.

        RegexNfa concatenation = new RegexNfa(left.Start, right.Final);
        left.Final.EpsilonTransitions.add(right.Start);

        return concatenation;
    }

    public static RegexNfa Regex(String regex) {

        if (regex.length() == 0 || regex.equals("e")) {
            State state = new State();
            return new RegexNfa(state, state);
        }
        if (regex.length() == 1) {
            State left = new State();
            State right = new State();
            if (regex.equals("a")) {
                left.TransitionA = right;
            } else if (regex.equals("b")) {
                left.TransitionB = right;
            } else {
                throw new IllegalStateException("Invalid regex: " + regex);
            }
            return new RegexNfa(left, right);
        }

        String leftUnion = ParserHelper.StringBeforeSameLevelUnion(regex);
        if (leftUnion.length() != regex.length()) {
            return Union(Regex(leftUnion), Regex(regex.substring(regex.length() - 1)));
        }

        // String is of the form "ab(..(...).)a*(...)*bb..."
        // IE, no unions to worry about. Next we concatenate the first term with the second term.

        if (regex.charAt(0) != '(') {
            return Concatenate(Regex(regex.substring(0, 1)), Regex(regex.substring(1)));
        }

        String group = ParserHelper.MatchParentheses(regex);
        if (regex.length() > group.length() && regex.charAt(group.length() + 1) == '*') {
            RegexNfa left = Star(Regex(group.substring(1, group.length() - 1)));
            RegexNfa right = Regex(regex.substring(group.length() + 2));
            return Concatenate(left, right);
        } else {
            RegexNfa left = Regex(group.substring(1, group.length() - 1));
            RegexNfa right = Regex(regex.substring((group.length() + 1)));
            return Concatenate(left, right);
        }
    }
}
