package gazeeebo.commands.gpacalculator;

public class ConverterGradetoScoreCommand {
    public double converter(final String grade) {
        double score = 0.0;
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
            score = 0.1; //mark as not counted into GPA
        }
        return score;
    }
}
