import java.util.Scanner;

public class Task1 {
    // Returns the sum of all positive divisors of n, including n itself.
    public static int sumOfDivisors(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return sum;
    }

    // Two numbers are friendly when their abundancy indices are equal.
    // Cross multiplication avoids rounding problems with double values.
    public static boolean areFriendly(int a, int b) {
        int sumA = sumOfDivisors(a);
        int sumB = sumOfDivisors(b);
        return (long) sumA * b == (long) sumB * a;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = input.nextInt();

        System.out.println("Friendly number pairs less than " + n + ":");

        boolean found = false;
        for (int i = 1; i < n; i++) {
            // Start from i + 1 so every pair is printed only once.
            for (int j = i + 1; j < n; j++) {
                if (areFriendly(i, j)) {
                    System.out.println("(" + i + ", " + j + ")");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No friendly number pairs found.");
        }

        input.close();
    }
}
