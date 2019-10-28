package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SetupCommand extends Command {

    private String userName;
    private String gender;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public SetupCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        userName = inputs.get(1);

        if (inputs.get(2).equals("boy")) {
            gender = "Mr. ";
        } else if (inputs.get(2).equals("girl")) {
            gender = "Ms. ";
        }

        String responseString = "Hello " + gender + userName + "! You have successfully set up your profile!";
        return responseString;
    }

}
