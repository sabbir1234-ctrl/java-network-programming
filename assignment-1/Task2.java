import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String text = input.nextLine();

        System.out.print("Enter the letter CH: ");
        char ch = input.next().charAt(0);

        // Ignore uppercase/lowercase differences.
        char target = Character.toLowerCase(ch);
        int count = 0;

        // Split the entered string into words separated by whitespace.
        String[] words = text.trim().split("\\s+");

        if (!text.trim().isEmpty()) {
            for (String word : words) {
                char first = Character.toLowerCase(word.charAt(0));
                char last = Character.toLowerCase(word.charAt(word.length() - 1));

                // Count a word once if it starts OR ends with CH.
                if (first == target || last == target) {
                    count++;
                }
            }
        }

        System.out.println("Number of words that start or end with '" + ch + "': " + count);
        input.close();
    }
}
