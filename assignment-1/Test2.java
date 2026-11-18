public class Test2 {
    public static void main(String[] args) {
        // Array of exactly 8 rectangular prisms.
        RectangularPrism[] prisms = {
                new RectangularPrism(2, 3, 4),
                new RectangularPrism(3, 4, 5),
                new RectangularPrism(5, 2, 7),
                new RectangularPrism(4, 4, 4),
                new RectangularPrism(6, 3, 5),
                new RectangularPrism(2, 8, 3),
                new RectangularPrism(7, 2, 6),
                new RectangularPrism(5, 5, 5)
        };

        RectangularPrism biggest = prisms[0];

        for (int i = 1; i < prisms.length; i++) {
            if (prisms[i].getVolume() > biggest.getVolume()) {
                biggest = prisms[i];
            }
        }

        System.out.println("Rectangular prism with the biggest volume:");
        biggest.print();
    }
}
