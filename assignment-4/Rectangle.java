public class Rectangle extends Polygon {
    private static final long serialVersionUID = 1L;
    private double a;
    private double b;

    // No-arguments constructor: all attributes have the default value 1.
    public Rectangle() {
        this(1, 1);
    }

    // Constructor with arguments.
    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public double getPerimeter() {
        return 2 * (a + b);
    }

    @Override
    public double getArea() {
        return a * b;
    }

    @Override
    public void printName() {
        System.out.println("Rectangle");
    }

    @Override
    public void print() {
        System.out.println("Rectangle with sides " + a + " and " + b
                + ", has a perimeter L=" + getPerimeter()
                + " and an area P=" + getArea() + ".");
    }
}
