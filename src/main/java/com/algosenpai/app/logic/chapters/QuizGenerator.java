package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.chapters.chapter1.ChapterSorting;
import com.algosenpai.app.logic.chapters.chapter2.ChapterLinkedList;
import com.algosenpai.app.logic.chapters.chapter3.ChapterBitmask;
import com.algosenpai.app.logic.models.QuestionModel;
import com.itextpdf.text.Chapter;

import java.util.ArrayList;
import java.util.Random;

public class QuizGenerator {

    /**
     * Generates the quiz according to the chapters specified by the user.
     * By default, all the chapters will be selected.
     * @param questionList the ArrayList of Questions to be filled.
     * @return the ArrayList with all the questions generated
     */

    public ArrayList<QuestionModel> generateQuiz(int selectedChapters, ArrayList<QuestionModel> questionList) {
        questionList = new ArrayList<>();

        ChapterSorting chapterSorting = new ChapterSorting();
        ChapterLinkedList chapterLinkedList = new ChapterLinkedList();
        ChapterBitmask chapterBitmask = new ChapterBitmask();
        switch (selectedChapters) {
        case 1 :
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
            break;
        case 2 :
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
            break;
        case 3 :
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterBitmask.generateQuestions());
            }
            break;
        default :
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int chapterToPick = random.nextInt(3);
                switch (chapterToPick) {
                case 0 :
                    questionList.add(ChapterSorting.generateQuestions());
                    break;
                case 1 :
                    questionList.add(ChapterLinkedList.generateQuestions());
                    break;
                case 2 :
                    questionList.add(ChapterBitmask.generateQuestions());
                    break;
                default :
                    break;
                }
            }
        }
        return questionList;
    }

}
