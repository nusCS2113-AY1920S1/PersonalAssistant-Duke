package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.MainApp;
import com.algosenpai.app.logic.chapters.chapter1.ChapterSorting;
import com.algosenpai.app.logic.chapters.chapter2.ChapterLinkedList;
import com.algosenpai.app.logic.chapters.chapter3.ChapterBitmask;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.utility.LogCenter;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class QuizGenerator {

    private static final Logger logger = LogCenter.getLogger(QuizGenerator.class);

    /**
     * Generates the quiz according to the chapters specified by the user.
     * By default, all the chapters will be selected.
     * @return the ArrayList with all the questions generated
     */
    public ArrayList<QuestionModel> generateQuiz(int selectedChapters) {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        ChapterSorting chapterSorting = new ChapterSorting();
        ChapterLinkedList chapterLinkedList = new ChapterLinkedList();
        ChapterBitmask chapterBitmask = new ChapterBitmask();
        switch (selectedChapters) {
        case 1 :
            logger.info("Generating quiz with questions from the Sorting chapter...");
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
            break;
        case 2 :
            logger.info("Generating quiz with questions from the Linked List chapter...");
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterLinkedList.generateQuestions());
            }
            break;
        case 3 :
            logger.info("Generating quiz with questions from the Bitmask chapter...");
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterBitmask.generateQuestions());
            }
            break;
        default :
            logger.info("Generating quiz with questions from all the three chapters... (no chapter specified");
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

    /**
     * Generates questions for the arcade mode.
     * @return A single question model.
     */
    public QuestionModel generateQuestion() {
        logger.fine("Generating question for arcade mode....");
        Random random = new Random();
        int chapterToPick = random.nextInt(3);
        switch (chapterToPick) {
        case 0 :
            return ChapterSorting.generateQuestions();
        case 1 :
            return ChapterLinkedList.generateQuestions();
        case 2 :
            return ChapterBitmask.generateQuestions();
        default :
            return null;
        }
    }

}
