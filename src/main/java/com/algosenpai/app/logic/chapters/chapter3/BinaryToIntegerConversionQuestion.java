package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;

public class BinaryToIntegerConversionQuestion extends Question {

    private static String binaryRepresentation;

    BinaryToIntegerConversionQuestion() {
        int ans = getRandomNumber(200,300);
        answer = String.valueOf(ans);
        binaryRepresentation = Integer.toBinaryString(ans);
        questionFormatter();
    }

    @Override
    public void questionFormatter() {
        question = "What is the integer representation of binary value "
                + binaryRepresentation + "?";
    }

}
