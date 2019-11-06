package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.Random;

public class ChapterBitmask {

    private static Random random = new Random();

    /**
     * Generates a random question related to bitmasking.
     * @return A question model containing to the random number being generated.
     */
    public static QuestionModel generateQuestions() {
        int questionType = random.nextInt(4);
        switch (questionType) {
        case 0 :
            return new LeastSignificantBitQuestion().execute();
        case 1 :
            return new BinaryToIntegerConversionQuestion().execute();
        case 2 :
            return new CountingBitsQuestion().execute();
        case 3 :
            return new BitwiseOperatorQuestion().execute();
        default :
            return null;
        }
    }


}

