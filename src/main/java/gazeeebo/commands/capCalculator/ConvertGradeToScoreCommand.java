package gazeeebo.commands.capCalculator;

/**
 * Coverts Grade to Integer score.
 */
public class ConvertGradeToScoreCommand {
    /**
     * A/A+ = 5.0.
     */
    private static final double A_SCORE = 5.0;
    /**
     * A- = 4.5.
     */
    private static final double A_MINUS_SCORE = 4.5;
    /**
     * B+ = 4.0.
     */
    private static final double B_PLUS_SCORE = 4.0;
    /**
     * B = 3.5.
     */
    private static final double B_SCORE = 3.5;
    /**
     * B- = 3.0.
     */
    private static final double B_MINUS_SCORE = 3.0;
    /**
     * C+ = 2.5.
     */
    private static final double C_PLUS_SCORE = 2.5;
    /**
     * C = 2.0.
     */
    private static final double C_SCORE = 2.0;
    /**
     * D+ = 1.5.
     */
    private static final double D_PLUS_SCORE = 1.5;
    /**
     * D = 1.0.
     */
    private static final double D_SCORE = 1.0;
    /**
     * F = 0.0.
     */
    private static final double F_SCORE = 0.0;
    /**
     * Modules without a grade score (S/US/CS) = 0.1.
     */
    private static final double DONT_COUNT_SCORE = 0.1;

    /**
     * Converts the alphabetical score to integer score.
     *
     * @param grade alphabetical score of the module
     * @return the integer score of the grade according to NUS score syytem
     */
    public double converter(final String grade) {
        double score;
        switch (grade) {
            case "A+":
            case "A":
                score = A_SCORE;
                break;
            case "A-":
                score = A_MINUS_SCORE;
                break;
            case "B+":
                score = B_PLUS_SCORE;
                break;
            case "B":
                score = B_SCORE;
                break;
            case "B-":
                score = B_MINUS_SCORE;
                break;
            case "C+":
                score = C_PLUS_SCORE;
                break;
            case "C":
                score = C_SCORE;
                break;
            case "D+":
                score = D_PLUS_SCORE;
                break;
            case "D":
                score = D_SCORE;
                break;
            case "F":
                score = F_SCORE;
                break;
            default:
                score = DONT_COUNT_SCORE;
                break;
        }
        return score;
    }
}
