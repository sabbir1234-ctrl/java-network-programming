public class RectangularPrism extends Rectangle {
    private static final long serialVersionUID = 1L;
    private double height;

    // No-arguments constructor: all attributes have the default value 1.
    public RectangularPrism() {
        super();
        this.height = 1;
    }

    // Constructor with arguments.
    public RectangularPrism(double a, double b, double height) {
        super(a, b);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Surface area of a rectangular prism.
    @Override
    public double getArea() {
        double a = getA();
        double b = getB();
        return 2 * (a * b + a * height + b * height);
    }

    public double getVolume() {
        return getA() * getB() * height;
    }

    @Override
    public void printName() {
        System.out.println("Rectangular prism");
    }

    @Override
    public void print() {
        System.out.println("Rectangular prism with a base with sides a= " + getA()
                + " and b=" + getB() + ", and a height " + height
                + ", has an area P =" + getArea()
                + " and a volume V=" + getVolume() + ".");
    }
}
