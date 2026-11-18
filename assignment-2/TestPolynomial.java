/**
 * Tests the functionality of the Polynomial class.
 */
public class TestPolynomial {

    public static void main(String[] args) {
        try {
            // P(x) = 2.5x^3 - 4x^2 + 3x - 7
            Polynomial p = new Polynomial(3);
            p.copyCoefficients(new double[]{-7, 3, -4, 2.5});
            System.out.print("p: ");
            p.print();
            System.out.println("Degree of p: " + p.getDegree());
            System.out.println("p(2) = " + p.evaluate(2));   // 20 - 16 + 6 - 7 = 3.0

            // Q(x) = x^2 + 1 (built with setCoefficient)
            Polynomial q = new Polynomial(2);
            q.setCoefficient(0, 1);
            q.setCoefficient(1, 0);
            q.setCoefficient(2, 1);
            System.out.print("q: ");
            q.print();
            System.out.println("q(3) = " + q.evaluate(3));   // 10.0

            // Sum
            Polynomial s = p.sum(q);
            System.out.print("p + q: ");
            s.print();                                       // 2.5x^3 - 3x^2 + 3x - 6
            System.out.println("(p+q)(2) = " + s.evaluate(2));

            // getCoefficient / setDegree
            System.out.println("Coefficient a_2 of p = " + p.getCoefficient(2));
            p.setDegree(5);
            p.setCoefficient(5, -1);
            System.out.print("p after setDegree(5): ");
            p.print();
            p.setDegree(1);
            System.out.print("p after setDegree(1): ");
            p.print();

            // Zero polynomial
            System.out.print("zero: ");
            new Polynomial(2).print();
        } catch (PolynomialException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        System.out.println("\n--- Testing exceptions ---");

        try {
            new Polynomial(Polynomial.MAX_DEGREE + 1);
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            new Polynomial(-2);
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            Polynomial p = new Polynomial(2);
            p.setCoefficient(3, 1.0);
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            Polynomial p = new Polynomial(2);
            p.getCoefficient(-1);
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            Polynomial p = new Polynomial(2);
            p.setDegree(20);
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            Polynomial p = new Polynomial(2);
            p.copyCoefficients(new double[]{1, 2});
        } catch (PolynomialException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
