//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SetupCommand extends Command {
    ArrayList<QuestionModel> quizList;

    int setupStage;

    AtomicBoolean isSettingUp;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public SetupCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Initializes setup command to start setup.
     * @param inputs user inputs.
     * @param setupStage to indicate which stage of the setup process the user is at.
     * @param isSettingUp is in the process of setting up.
     */
    public SetupCommand(ArrayList<String> inputs, int setupStage,
                       AtomicBoolean isSettingUp) {
        this(inputs);
        this.setupStage = setupStage;
        this.isSettingUp = isSettingUp;
    }


    @Override
    public String execute() {
        if (setupStage == 1) {
            return checkStatus();
        } else if (setupStage == 2) {
            return "Are you a boy or a girl?";
        } else if (setupStage == 3) {
            //set the variable isBoy to true or false
            return "You're all set! Time to start your journey to become an AlgoSenpai!";
        } else {
            return checkStatus();
        }
    }

    /**
     * Checks whether it is the user's first time using the application.
     * @return The respective string to be displayed to the situation.
     */
    private String checkStatus() {
        if (setupStage ==  1) {
            return "Oh it seems that it is your first time here! Can I get your name?";
        } else {
            return " Welcome back!";
        }
    }
}
