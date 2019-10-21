package com.algosenpai.app;

import static java.lang.Integer.parseInt;

/**
 * Chapter Statistics. Holds the data about each chapter.
 *
 */
public class ChapterStat {

    String chapterName;
    int chapterNumber;
    int attempts;
    int totalAnswered;
    int correctAnswers;
    int wrongAnswers;
    double percentage;
    String comments;

    /**
     * Constructor.
     * @param chapterName The name of the chapter.
     * @param chapterNumber The chapter number.
     * @param attempts The number of times that chapter has been attempted.
     * @param totalAnswered The total answered questions.
     * @param correctAnswers The number of correctly answered questions.
     * @param wrongAnswers The number of wrongly answered questions.
     * @param percentage The percentage of answers answered correctly.
     * @param comments The comments given for each chapter.
     */
    public ChapterStat(String chapterName, int chapterNumber, int attempts,
                       int totalAnswered, int correctAnswers, int wrongAnswers,
                       double percentage, String comments) {
        this.chapterName = chapterName;
        this.chapterNumber = chapterNumber;
        this.attempts = attempts;
        this.totalAnswered = totalAnswered;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentage = percentage;
        this.comments = comments;
    }

    /**
     * Makes a new copy of the Chapter.
     * @param referenceChapter The chapter to be copied.
     */
    ChapterStat(ChapterStat referenceChapter) {
        this.chapterName = referenceChapter.chapterName;
        this.chapterNumber = referenceChapter.chapterNumber;
        this.attempts = referenceChapter.attempts;
        this.totalAnswered = referenceChapter.totalAnswered;
        this.correctAnswers = referenceChapter.correctAnswers;
        this.wrongAnswers = referenceChapter.wrongAnswers;
        this.percentage = referenceChapter.percentage;
        this.comments = referenceChapter.comments;
    }

    /**
     * Converts the chapter stats to a string.
     * @return the String.
     */
    @Override
    public String toString() {
        String result = "";
        result += "Chapter " + chapterNumber + " : " + chapterName + '\n';
        result += "Total Attempts made : " + attempts + '\n';
        result += "Total Questions answered : " + totalAnswered + '\n';
        result += "Total Questions correct : " + correctAnswers + '\n';
        result += "Total Questions wrong : " + wrongAnswers + '\n';
        result += "Percentage of Questions correct : " + percentage + '\n';
        result += "Comments : \"" + comments + "\"\n";
        return result;
    }

    /**
     * Recalculates the percentage and wrong answers when the correct answers or total answers is updated.
     */
    public void recalculateStats() {
        if (totalAnswered == 0) {
            return; // to avoid division by 0
        }
        percentage = 100.0 * correctAnswers / (double) totalAnswered;
        wrongAnswers = totalAnswered - correctAnswers;
    }

    /**
     * Given the string representation, it returns the ChapterStat object.
     * TODO Handle invalid string.
     * @param string The string version of the ChapterStat (obtained by calling toString()).
     * @return The ChapterStat object.
     */
    public static ChapterStat parseString(String string) {

        // Split the string into individual lines.
        String[] lines = string.split("\n");

        int chapterNumber = Integer.parseInt(lines[0].split(" ")[1]);
        // Each parameter comes after a colon (:), so we split by : and take the second element in the array.
        String chapterName = lines[0].split(":")[1].substring(1);
        int attempts = Integer.parseInt(lines[1].split(":")[1].trim());
        int totalAnswered = parseInt(lines[2].split(":")[1].trim());
        int correctAnswers = Integer.parseInt(lines[3].split(":")[1].trim());
        int wrongAnswers = Integer.parseInt(lines[4].split(":")[1].trim());
        double percentage = Double.parseDouble(lines[5].split(":")[1].trim());
        String comments = lines[6].split(":")[1].trim();
        // remove the quotes
        comments = comments.substring(1,comments.length() - 1);

        return new ChapterStat(chapterName,chapterNumber,attempts,
                totalAnswered,correctAnswers,wrongAnswers,percentage,comments);
    }

    /**
     * Overriding the equals method so JUnit can work.
     * We manually check if each property is equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChapterStat) {

            ChapterStat other = (ChapterStat) obj;
            return (
                    chapterName.equals(other.chapterName)
                    && chapterNumber == other.chapterNumber
                    && attempts == other.attempts
                    && totalAnswered == other.totalAnswered
                    && correctAnswers == other.correctAnswers
                    && wrongAnswers == other.wrongAnswers
                    && percentage == other.percentage
                    && comments.equals(other.comments)
                    );
        } else {
            return false;
        }
    }
}
