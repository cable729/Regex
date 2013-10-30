public class Regex {

    public static RegexNfa Union(RegexNfa left, RegexNfa right) {
        // Create a new start and end state. The start state epsilons to the left
        // and right start states. Then epsilon left and right final states to new
        // final state.

        RegexNfa union = new RegexNfa(new State(null, null), new State(null, null));

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

        String left = ParserHelper.StringBeforeSameLevelUnion(regex);
        if (left.length() != regex.length()) {
            return Union(Regex(left), Regex(regex.substring(regex.length() - 1)));
        }


        // TODO: must never return null
        return null;
    }
}
