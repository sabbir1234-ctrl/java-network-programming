import java.io.*;

public class Task1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RectangularPrism[] prisms = {
            new RectangularPrism(2, 3, 4),
            new RectangularPrism(3, 4, 5),
            new RectangularPrism(5, 2, 7),
            new RectangularPrism(4, 4, 4),
            new RectangularPrism(6, 3, 5),
            new RectangularPrism(2, 8, 3),
            new RectangularPrism(7, 2, 6),
            new RectangularPrism(1, 2, 3),
            new RectangularPrism(8, 5, 2)
        };

        // Write 9 prisms to the binary file.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("prisms9.ser"))) {
            out.writeInt(prisms.length);
            for (RectangularPrism p : prisms) {
                out.writeObject(p);
            }
        }

        // Read the file back and display the content on screen.
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("prisms9.ser"))) {
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                RectangularPrism p = (RectangularPrism) in.readObject();
                p.print();
            }
        }
    }
}
