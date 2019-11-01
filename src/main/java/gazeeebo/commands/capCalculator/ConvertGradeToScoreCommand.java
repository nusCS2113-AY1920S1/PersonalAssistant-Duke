package gazeeebo.commands.capCalculator;

/**
 * Coverts Grade to Integer score.
 */
public class ConvertGradeToScoreCommand {
    /**
     * Converts the alphabetical score to integer score.
     * @param grade alphabetical score of the module
     * @return the integer score of the grade according to NUS score syytem
     */
    public double converter(final String grade) {
        double score;
        switch (grade) {
            case "A+":
            case "A":
                score = 5.0;
                break;
            case "A-":
                score = 4.5;
                break;
            case "B+":
                score = 4.0;
                break;
            case "B":
                score = 3.5;
                break;
            case "B-":
                score = 3.0;
                break;
            case "C+":
                score = 2.5;
                break;
            case "C":
                score = 2.0;
                break;
            case "D+":
                score = 1.5;
                break;
            case "D":
                score = 1.0;
                break;
            case "F":
                score = 0.0;
                break;
            default:
                score = 0.1;
                break;
        }
        return score;
    }
}
