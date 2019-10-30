package javacake.quiz;

import javacake.exceptions.DukeException;

public interface QuizManager {
    String getQuestion(int index);

    String parseInput(int index, String input) throws DukeException;
}
