import java.io.*;
public class Task1 {
    public static void main(String[] args) throws IOException {
        RectangularPrism[] prisms = {
            new RectangularPrism(2, 3, 4),
            new RectangularPrism(3, 4, 5),
            new RectangularPrism(5, 2, 7),
            new RectangularPrism(4, 4, 4),
            new RectangularPrism(6, 3, 5),
            new RectangularPrism(2, 8, 3),
            new RectangularPrism(7, 2, 6)
        };
        // The original Assignment 1 print() writes to System.out.
        // Redirect it to the file to preserve the exact original format.
        PrintStream original = System.out;
        try (PrintStream file = new PrintStream(new FileOutputStream("RectangularPrisms7.txt"), true, "UTF-8")) {
            System.setOut(file);
            for (RectangularPrism prism : prisms) {
                prism.print();
            }
        } finally {
            System.setOut(original);
        }
        // Read the file back and display every line on screen.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("RectangularPrisms7.txt"), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
