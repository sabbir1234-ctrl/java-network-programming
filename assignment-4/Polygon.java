import java.io.Serializable;

public abstract class Polygon implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract double getPerimeter();
    public abstract double getArea();
    public abstract void printName();
    public abstract void print();
}
