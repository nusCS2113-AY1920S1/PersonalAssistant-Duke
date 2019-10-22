package gazeeebo.commands.capCalculator;

public class convertGradetoScoreCommand {
    /**
     * Converts the alphabetical score to integer score.
     * @param grade alphabetical score of the module
     * @return the integer score of the grade according to NUS score syytem
     */
    public double converter(String grade) {
        double score;
        if (grade.equals("A") || grade.equals("A+")) {
            score = 5.0;
        } else if(grade.equals("A-")) {
            score = 4.5;
        } else if(grade.equals("B+")) {
            score = 4.0;
        } else if(grade.equals("B")) {
            score = 3.5;
        } else if(grade.equals("B-")) {
            score = 3.0;
        } else if(grade.equals("C+")) {
            score = 2.5;
        } else if(grade.equals("C")) {
            score = 2.0;
        } else if(grade.equals("D+")) {
            score = 1.5;
        } else if(grade.equals("D")) {
            score = 1.0;
        } else if(grade.equals("F")) {
            score = 0.0;
        } else {
            score = 0.1; //denote this number to make it not counted into the CAP
        }
        return score;
    }
}
