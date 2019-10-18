package com.algosenpai.app;

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

}
