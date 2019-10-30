package com.algosenpai.app.logic.chapters.chapter2;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.Random;

public class ChapterLinkedList {


    private static Random random = new Random();

    /**
     * Generates a random question related to linked lists.
     * @return a question model according to the random number being generated.
     */
    public static QuestionModel generateQuestions() {
        int questionType = random.nextInt(4);
        switch (questionType) {
        case 0:
            return new StackPopPushQuestion().execute();
        case 1:
            return new QueuePopPushQuestion().execute();
        case 2:
            return new SingleInsertLinkedListQuestion().execute();
        case 3:
            return new PseudoCodeQuestion().execute();
        default:
            return null;
        }
    }

}