package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.chapters.chapter1.ChapterSorting;
import com.algosenpai.app.logic.chapters.chapter2.ChapterLinkedList;
import com.algosenpai.app.logic.chapters.chapter3.ChapterBitmask;
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
            //by default, generate questions from all the chapters
            for (int i = 0; i < 4; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
            for (int i = 4; i < 7; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
            for (int i = 7; i < 10; i++) {
                questionList.add(ChapterBitmask.generateQuestions());
            }
        } else if (selectedChapters == 1) {
            //generate only sorting questions
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
        } else if (selectedChapters == 2) {
            //generate only linked list questions
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
        } else if (selectedChapters == 3) {
            //generate both the sorting and linked list questions
            for (int i = 0; i < 5; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
            for (int i = 5; i < 10; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
        } else if (selectedChapters == 7) {
            //generate questions from all the chapters
            for (int i = 0; i < 4; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
            for (int i = 4; i < 7; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
            for (int i = 7; i < 10; i++) {
                questionList.add(ChapterBitmask.generateQuestions());
            }
        }
        return questionList;
    }

}
