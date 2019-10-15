package com.algosenpai.app;

/**
 * Chapter Statistics. Holds the data about each chapter.
 *
 */
public class ChapterStat {

    String chapterName;
    int totalAnswered;
    int correctAnswers;
    int attempts;

    /**
     * Constructor. Chapter Name has a default value of "Untitled Chapter"
     * @param totalAnswered The total answered questions.
     * @param correctAnswers The number of correctly answered questions.
     * @param attempts The number of times that chapter has been attempted.
     */
    ChapterStat(int totalAnswered, int correctAnswers, int attempts) {
        this.chapterName = "Untitled Chapter";
        this.totalAnswered = totalAnswered;
        this.correctAnswers = correctAnswers;
        this.attempts = attempts;
    }

    /**
     * Constructor.
     * @param chapterName The name of the chapter.
     * @param totalAnswered The total answered questions.
     * @param correctAnswers The number of correctly answered questions.
     * @param attempts The number of times that chapter has been attempted.
     */
    public ChapterStat(String chapterName, int totalAnswered, int correctAnswers, int attempts) {
        this.chapterName = chapterName;
        this.totalAnswered = totalAnswered;
        this.correctAnswers = correctAnswers;
        this.attempts = attempts;
    }

}
