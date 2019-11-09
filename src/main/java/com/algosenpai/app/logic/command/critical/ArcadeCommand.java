package com.algosenpai.app.logic.command.critical;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArcadeCommand extends Command {

    private static int highScore = 0;
    private AtomicBoolean isArcadeMode;
    private QuizGenerator quizGenerator = new QuizGenerator();

    private static QuestionModel previousQuestion;
    private static QuestionModel currQuestion;

    /**
     * Create new command.
     *
     * @param inputs User's input.
     */
    public ArcadeCommand(ArrayList<String> inputs, AtomicBoolean isArcadeMode) {
        super(inputs);
        this.isArcadeMode = isArcadeMode;
        currQuestion = quizGenerator.generateQuestion();
    }

    @Override
    public String execute() {
        if (!isArcadeMode.get()) {
            isArcadeMode.set(true);
            String question = currQuestion.getQuestion();
            previousQuestion = currQuestion.copy();
            currQuestion = quizGenerator.generateQuestion();
            return question;
        } else {
            if (inputs.size() > 0) {
                String userAnswer = extractUserAnswerFromInput();
                previousQuestion.setUserAnswer(userAnswer);
                if (previousQuestion.checkAnswer()) {
                    previousQuestion = currQuestion.copy();
                    currQuestion = quizGenerator.generateQuestion();
                    highScore++;
                    return previousQuestion.getQuestion();
                } else {
                    return reset();
                }
            } else {
                return reset();
            }
        }
    }

    private String reset() {
        isArcadeMode.set(false);
        previousQuestion = null;
        currQuestion = null;
        int currentScore = highScore;
        highScore = 0;
        return "Your Arcade High Score is : " + currentScore;
    }

    /**
     * Parses the user input into a string format.
     * @return The string containing the user's answer.
     */
    private String extractUserAnswerFromInput() {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < inputs.size() - 1; i++) {
            answer.append(inputs.get(i)).append(", ");
        }
        answer.append(inputs.get(inputs.size() - 1));
        return answer.toString();
    }
}
