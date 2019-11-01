package com.algosenpai.app.logic.command;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizCommand extends Command {

    /**
     * Initializes commands for quiz.
     * @param inputs input from user.
     */
    public QuizCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "You are taking a quiz. Please prefix commands with quiz: quiz   < answer | back | next | end >";
    }
}
