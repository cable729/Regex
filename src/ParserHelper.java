
public class ParserHelper {
    public static String StringInsideParentheses(String input, int leftParen) {
        if (input.charAt(leftParen) != '(') {
            throw new IllegalArgumentException("There must be a left parentheses at input[leftParen].");
        }

        int parenCount = 1;
        for (int i = leftParen; i < input.length(); i++) {
            if (input.charAt(i) == '(') parenCount++;
            if (input.charAt(i) == ')') parenCount--;

            if (parenCount == 0) return input.substring(leftParen + 1, i - 1);
        }

        // No matching parentheses.
        throw new IllegalArgumentException("The input string is missing a parenthesis.");
    }

    public static String StringBeforeSameLevelUnion(String input) {
        int parenthesesCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') parenthesesCount++;
            if (input.charAt(i) == ')') parenthesesCount--;

            if (parenthesesCount == 0 && input.charAt(i) == '|') return input.substring(0, i);
        }

        // This function returns the whole string if there are no unions.
        return input;
    }
}
