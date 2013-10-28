
public class ParserHelper {
    public static String StringInsideParentheses(String input, int leftParen){
        if (input.charAt(leftParen) != '('){
            throw new IllegalArgumentException("There must be a left parentheses at input[leftParen].");
        }

        int parenCount = 1;
        for (int i = leftParen; i < input.length(); i++){
            if (input.charAt(i) == '(') parenCount++;
            if (input.charAt(i) == ')') parenCount--;

            if (parenCount == 0) return input.substring(leftParen + 1, i - 1);
        }

        // No matching parentheses.
        throw new IllegalArgumentException("The input string is missing a parenthesis.");
    }

    public static String StringBeforeSameLevelUnion(String input, int start){
        int parenCount = 0;
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '(') parenCount++;
            if (input.charAt(i) == ')') parenCount--;

            if (parenCount == 0 && input.charAt(i) == '|') return input.substring(start, i);
        }

        // This function returns the rest of the string if there are no more unions.
        return input.substring(start);
    }

    private static int findMatchingLeftParenthesis(String input, int rightParen){
        int parenCount = -1;

        for (int i = rightParen; i >= 0; i--){
            if (input.charAt(i) == '(') parenCount++;
            if (input.charAt(i) == ')') parenCount--;

            if (parenCount== 0) return i;
        }

        throw new IllegalArgumentException("The input string is missing a parenthesis.");
    }

    public static String ConvertToExplicitParenthesizedRegex(String input){

        // We first put explicit parentheses next to repeat characters. We start at i = 1 because
        // repeat characters can only exist after the first character.
        for (int i = 1; i < input.length(); i++){

            // We're only interested in inserting parentheses where they do not already exists.
            if (input.charAt(i-1) != ')' && input.charAt(i) == '*') {
                // Insert a parentheses around the last character. For example, a* becomes (a)*
                input = input.substring(0,i-1) + '(' + input.substring(i-1, i) + ')' + input.substring(i);
                // We also must add 2 to i because we inserted 2 characters.
                i += 2;
            }
        }

        // Next we put explicit parentheses around implicit concatenation.
        for (int i = 0; i < input.length(); i++){

            if (input.charAt())
        }

    }
}
