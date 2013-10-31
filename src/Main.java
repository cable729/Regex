import java.util.Scanner;

public class Main {

    public static void Main(String[] args) {

        Scanner input = new Scanner(System.in);

        RegexNfa regex = Regex.Regex(input.nextLine());
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (regex.Accept(line)) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
    }
}
