package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.models.QuestionModel;
import java.util.Random;

public class ChapterSorting {

    private static Random random = new Random();

    /**
     * Generates the question by using a random number to determine which of the
     * sub-questions to ask.
     * 
     * @return A question class that contains the question and expected answer.
     */

    public static QuestionModel generateQuestions() {
        int questionType = random.nextInt(4);
        switch (questionType) {
        case 0:
            return new BubbleSortPassesQuestion().execute();
        case 1:
            return new QuickSortPivotQuestion().execute();
        case 2:
            return new InsertionSortSwapQuestion().execute();
        case 3:
            return new SelectionSortSwapQuestion().execute();
        default:
            return null;
        }
    }

}