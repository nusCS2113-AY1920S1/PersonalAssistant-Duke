package com.algosenpai.app.chapters.chapter1;

import com.algosenpai.app.logic.chapters.chapter1.BubbleSortPassesQuestion;
import com.algosenpai.app.logic.models.QuestionModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BubbleSortPassesQuestionTest {

    /**
     * Creates my own BubbleSortPassesQuestion then checks if the parameters are correct.
     */
    @Test
    public void questionTesting() {
        int[] arr = {5,4,3,2,1};
        ArrayList<Integer> array = new ArrayList<>();
        for (int value : arr) {
            array.add(value);
        }
        ArrayList<String> myWrongAnswers = new ArrayList<>();
        myWrongAnswers.add("[4, 3, 2, 1, 5]");
        myWrongAnswers.add("[ 4 , 3 , 2 , 1 , 5 ]");
        myWrongAnswers.add("");
        myWrongAnswers.add("[1, 2, 3, 4, 5]");
        myWrongAnswers.add("[5, 4, 3, 2, 1]");
        String question = "An array of " + 5 + " elements underwent the following Bubble Sort Algorithm : "
                + "[5, 4, 3, 2, 1]" + "\n";
        question += "What would be the new configuration of the elements after " + 1 + " passes?\n"
                + "Please provide your answer in comma-separated format. e.g. x, y, z, ...\n\n";
        question += "for (int i = 0; i < passes; i++) {\n" + "   for (int j = 0; j < arr.size - 1 - i; j ++) {\n"
                + "       if (arr[j] > arr[j + 1]) {\n" + "            swap (arr[j], arr[j+1]);\n" + "       }\n"
                + "   }\n" + "}\n\n";
        QuestionModel questionModel = new BubbleSortPassesQuestion(5,array, 1).execute();
        assertEquals(questionModel.getQuestion(), question);
        String myCorrectAnswer = "4, 3, 2, 1, 5";
        assertEquals(questionModel.getAnswer(), myCorrectAnswer);
        questionModel.setUserAnswer(myCorrectAnswer);
        assertTrue(questionModel.checkAnswer());
        for (String currWrongAnswer : myWrongAnswers) {
            assertNotEquals(currWrongAnswer, questionModel.getAnswer());
            questionModel.setUserAnswer(currWrongAnswer);
            assertFalse(questionModel.checkAnswer());
        }
    }
}
