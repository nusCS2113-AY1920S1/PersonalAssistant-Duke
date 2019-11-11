package statistics;

/**
 * This class creates an object of type GraduateStats that allows to store all the details related to a degree in a particular year
 * An ArrayList of this type is then declared for storage of statistics
 *
 */
public class GraduateStats {
    public String a;
    public Double b;
    public Integer c;

    public GraduateStats(String a, Double b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
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
     * @return a the employment percentage rate of that year
     */

    public Double getB() {
        return b;
    }

    /**
     *
     * @return c the basic mean salary in that year
     */
    public Integer getC() {
        return c;
    }
}
