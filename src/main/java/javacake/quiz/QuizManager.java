package javacake.quiz;

import javacake.exceptions.CakeException;

public interface QuizManager {
    String getQuestion(int index);

    String parseInput(int index, String input) throws CakeException;
}
