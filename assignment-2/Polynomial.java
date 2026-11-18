/**
 * Represents a polynomial with real-valued coefficients.
 * coefficients[i] stores the coefficient a_i of x^i.
 */
public class Polynomial {

    public static final int MAX_DEGREE = 15;

    private double[] coefficients;
    private int degree;

    /**
     * Creates a polynomial of the given degree with all coefficients set to 0.
     */
    public Polynomial(int degree) throws PolynomialException {
        checkDegree(degree);
        this.coefficients = new double[MAX_DEGREE + 1];
        this.degree = degree;
    }

    private static void checkDegree(int degree) throws PolynomialException {
        if (degree < 0) {
            throw new PolynomialException("Degree cannot be negative: " + degree);
        }
        if (degree > MAX_DEGREE) {
            throw new PolynomialException(
                    "Degree " + degree + " exceeds MAX_DEGREE = " + MAX_DEGREE);
        }
    }

    private void checkPosition(int position) throws PolynomialException {
        if (position < 0 || position > degree) {
            throw new PolynomialException(
                    "Position " + position + " is outside the valid range [0, " + degree + "]");
        }
    }

    /**
     * Copies coefficients (a_0, a_1, ...) from the given array into the polynomial.
     * The array must contain exactly degree + 1 elements.
     */
    public void copyCoefficients(double[] source) throws PolynomialException {
        if (source == null) {
            throw new PolynomialException("Source array is null");
        }
        if (source.length != degree + 1) {
            throw new PolynomialException("Array length " + source.length
                    + " does not match the required length " + (degree + 1));
        }
        for (int i = 0; i <= degree; i++) {
            coefficients[i] = source[i];
        }
    }

    public void setCoefficient(int position, double value) throws PolynomialException {
        checkPosition(position);
        coefficients[position] = value;
    }

    public double getCoefficient(int position) throws PolynomialException {
        checkPosition(position);
        return coefficients[position];
    }

    /**
     * Sets a new degree. If the degree is increased, the new coefficients are 0;
     * if it is decreased, the dropped coefficients are discarded.
     */
    public void setDegree(int newDegree) throws PolynomialException {
        checkDegree(newDegree);
        for (int i = newDegree + 1; i <= MAX_DEGREE; i++) {
            coefficients[i] = 0;
        }
        this.degree = newDegree;
    }

    public int getDegree() {
        return degree;
    }

    /**
     * Prints the polynomial, e.g. P(x) = 2.5x^3 - 4x^2 + 3x - 7
     */
    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P(x) = ");
        boolean first = true;

        for (int i = degree; i >= 0; i--) {
            double c = coefficients[i];
            if (c == 0) {
                continue;
            }
            double abs = Math.abs(c);

            if (first) {
                if (c < 0) {
                    sb.append("-");
                }
            } else {
                sb.append(c < 0 ? " - " : " + ");
            }

            // the coefficient 1 is omitted in front of x, but kept for the constant term
            if (abs != 1 || i == 0) {
                sb.append(format(abs));
            }
            if (i >= 1) {
                sb.append("x");
            }
            if (i >= 2) {
                sb.append("^").append(i);
            }
            first = false;
        }

        if (first) {
            sb.append("0");
        }
        return sb.toString();
    }

    private static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    /**
     * Evaluates the polynomial at x using Horner's method.
     */
    public double evaluate(double x) {
        double result = 0;
        for (int i = degree; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        return result;
    }

    /**
     * Returns a new polynomial that is the sum of this and other.
     */
    public Polynomial sum(Polynomial other) throws PolynomialException {
        if (other == null) {
            throw new PolynomialException("Other polynomial is null");
        }
        int maxDeg = Math.max(this.degree, other.degree);
        Polynomial result = new Polynomial(maxDeg);
        for (int i = 0; i <= maxDeg; i++) {
            double a = (i <= this.degree) ? this.coefficients[i] : 0;
            double b = (i <= other.degree) ? other.coefficients[i] : 0;
            result.coefficients[i] = a + b;
        }
        return result;
    }
}
