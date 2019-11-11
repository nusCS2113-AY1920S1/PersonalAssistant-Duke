package com.algosenpai.app.logic.command.utility.print;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.UserStats;
import java.util.ArrayList;

public class PrintCommandFactory extends Command {

    private ArrayList<QuestionModel> quizList;
    private ArrayList<QuestionModel> archiveList;
    private UserStats userStats;


    /**
     * Initializes print command factory to decide what print action to take.
     * @param inputs The input from the user.
     * @param userStats The user's stats.
     * @param archiveList The user's archive list.
     * @param quizList The user's quiz list.
     */
    public PrintCommandFactory(ArrayList<String> inputs, UserStats userStats,
                        ArrayList<QuestionModel> archiveList, ArrayList<QuestionModel> quizList) {
        super(inputs);
        this.userStats = userStats;
        this.archiveList = archiveList;
        this.quizList = quizList;
    }

    /**
     * Returns message to show users how to use print functionality.
     * @return message to show print functionality.
     */
    @Override
    public String execute() {
        if (inputs.size() != 3) {
            return new PrintCommand(inputs).execute();
        }
        try {
            switch (inputs.get(1)) {
            case "user":
                return new PrintUserCommand(inputs, userStats).execute();
            case "archive":
                return new PrintArchiveCommand(inputs, archiveList).execute();
            case "quiz":
                return new PrintQuizCommand(inputs, quizList).execute();
            default:
                return new PrintCommand(inputs).execute();
            }
        } catch (IndexOutOfBoundsException e) {
            return new PrintCommand(inputs).execute();
        }
    }
}
