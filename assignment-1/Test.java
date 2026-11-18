public class Test {
    public static void main(String[] args) {
        // Test Rectangle with the no-arguments constructor.
        Rectangle rectangle1 = new Rectangle();
        rectangle1.printName();
        rectangle1.print();

        System.out.println();

        // Test Rectangle with the constructor with arguments and setters/getters.
        Rectangle rectangle2 = new Rectangle(4, 6);
        rectangle2.setA(5);
        rectangle2.setB(8);
        System.out.println("Rectangle a = " + rectangle2.getA());
        System.out.println("Rectangle b = " + rectangle2.getB());
        rectangle2.print();

        System.out.println();

        // Test RectangularPrism with the no-arguments constructor.
        RectangularPrism prism1 = new RectangularPrism();
        prism1.printName();
        prism1.print();

        System.out.println();

        // Test RectangularPrism with the constructor with arguments.
        RectangularPrism prism2 = new RectangularPrism(3, 4, 5);
        prism2.setHeight(6);
        System.out.println("Prism height = " + prism2.getHeight());
        prism2.print();
    }
}
