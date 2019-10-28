package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;

public class QuizGenerator {

    /**
     * Generates the quiz according to the chapters specified by the user.
     * By default, all the chapters will be selected.
     * @param questionList the ArrayList of Questions to be filled.
     * @return the ArrayList with all the questions generated
     */

    public ArrayList<QuestionModel> generateQuiz(int selectedChapters, ArrayList<QuestionModel> questionList) {
        questionList = new ArrayList<>();

        if (selectedChapters == 0) {
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
        } else if (selectedChapters == 2) {
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
        } else if (selectedChapters == 3) {
            questionList.add(ChapterBitmask.generateQuestions());
        }
        return questionList;
    }
}
