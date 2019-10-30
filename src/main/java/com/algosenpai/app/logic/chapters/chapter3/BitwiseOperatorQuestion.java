package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;

public class BitwiseOperatorQuestion extends Question {

    private static int firstNumber;
    private static int secondNumber;
    private static String bitwiseOperator;

    BitwiseOperatorQuestion() {
        //Bounds the two numbers to be operated on between 40 and 129.
        firstNumber = getRandomNumber(40,90);
        secondNumber = getRandomNumber(40,90);
        int ans;
        //Generates a random number to decide which operator to use.
        int randomNumber = getRandomNumber(0,3);
        switch (randomNumber) {
        case 0 :
            bitwiseOperator = " AND ";
            ans = firstNumber & secondNumber;
            break;
        case 1 :
            bitwiseOperator = " OR ";
            ans = firstNumber | secondNumber;
            break;
        case 2 :
            bitwiseOperator = " XOR ";
            ans = firstNumber ^ secondNumber;
            break;
        default :
            bitwiseOperator = null;
            ans = -1;
            break;
        }
        answer = String.valueOf(ans);
        questionFormatter();
    }

    @Override
    public void questionFormatter() {
        question = "What is the integer result of " + firstNumber + bitwiseOperator + secondNumber + "?";
    }

}
