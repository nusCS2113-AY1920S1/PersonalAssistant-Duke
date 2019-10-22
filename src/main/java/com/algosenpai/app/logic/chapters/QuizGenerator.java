package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.Question;

import java.util.ArrayList;

public class QuizGenerator {

    /**
     * Generates the quiz according to the chapters specified by the user.
     * By default, all the chapters will be selected.
     * @param questionList the ArrayList of Questions to be filled.
     * @return the ArrayList with all the questions generated
     */
    public ArrayList<Question> generateQuiz(int selectedChapters, ArrayList<Question> questionList) {
        questionList = new ArrayList<Question>();

        if (selectedChapters == 0) {
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
        } else {
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
        }

        return questionList;
    }
}
