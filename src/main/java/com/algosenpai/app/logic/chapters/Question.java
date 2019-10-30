package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.Random;

public abstract class Question {

    private static Random random = new Random();
    protected static String question;
    protected static String answer;

    /**
     * Packages the question.
     * 
     * @return The QuestionModel containing the question and answer.
     */
    public QuestionModel execute() {
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Generates a random number.
     * 
     * @param minimum The minimum possible number to be generated.
     * @param bound   The range of values to be added to the minimum.
     * @return The number.
     */
    protected static int getRandomNumber(int minimum, int bound) {
        return random.nextInt(bound) + minimum;
    }

    /**
     * Formats the question accordingly.
     */
    public abstract void questionFormatter();
}