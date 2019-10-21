package com.algosenpai.app.chapters;


import com.algosenpai.app.logic.Question;
import com.algosenpai.app.logic.chapters.ChapterSorting;

import java.util.ArrayList;

public class QuizGenerator {

    public ArrayList<Question> generateQuiz(int selectedChapters, ArrayList<Question> questionList) {
        questionList = new ArrayList<Question>();

        if (selectedChapters == 1) {
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
