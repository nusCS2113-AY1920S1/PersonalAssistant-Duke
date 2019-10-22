package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class QuizCommand extends Command {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    AtomicBoolean isQuizMode;

    AtomicBoolean isNewQuiz;

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private QuizCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    private QuizCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
    }

    /**
     * Initializes quiz command to start quiz.
     * @param command quiz command.
     * @param quizList quiz.
     * @param questionNumber question number.
     * @param isQuizMode is quiz mode.
     * @param isNewQuiz is quiz initialize.
     */
    public QuizCommand(Command command, ArrayList<QuestionModel> quizList,
                       AtomicInteger questionNumber, AtomicBoolean isQuizMode, AtomicBoolean isNewQuiz) {
        this(command);
        this.quizList = quizList;
        this.isQuizMode = isQuizMode;
        this.questionNumber = questionNumber;
        this.isNewQuiz = isNewQuiz;
    }

    @Override
    public String execute() {
        quizList.get(questionNumber.get()).setAnswer(getUserString());
        questionNumber.incrementAndGet();

        if (questionNumber.get() < 10) {
            return quizList.get(questionNumber.get()).getQuestion();
        } else if (questionNumber.get() == 10) {
            int correctCount = 0;
            for (int i = 0; i < 10; i++) {
                QuestionModel currQuestion = quizList.get(i);
                if (currQuestion.checkAnswer()) {
                    correctCount++;
                }
            }
            questionNumber.set(0);
            isQuizMode.set(false);
            isNewQuiz.set(true);
            return "You got " + correctCount + "/10 questions correct!";
        }
        return "quiz";
    }
}
