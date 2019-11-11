package statistics;

/**
 * This class creates an object of type CohortStats that allows to store all the details related to a degree in a particular year
 * An ArrayList of this type is then declared for storage of statistics
 *
 */
public class CohortStats {
    public String a;
    public Integer b;
    public Integer c;
    public Integer d;

    public CohortStats(String a, Integer b, Integer c, Integer d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     *
     * @return a the degree
     */
    public String getA() {
        return a;
    }

    /**
     *
     * @return b the number of male students in that degree for a particular year
     */

    public Integer getB() {
        return b;
    }

    /**
     *
     * @return c the number of female students in that degree for a particular year
     */

    public Integer getC() {
        return c;
    }

    /**
     *
     * @return d the number of total students in that degree for a particular year
     */
    public Integer getD() { return d; }
}
