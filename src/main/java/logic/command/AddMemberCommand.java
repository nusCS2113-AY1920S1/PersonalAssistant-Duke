package logic.command;

import common.LoggerController;
import model.Model;
import common.DukeException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddMemberCommand extends Command {
    public static final String COMMAND_WORD = "member";
    public static final String FEEDBACK_MESSAGE = "You have created a new member: ";
    private String name;
    private List<String> skills;

    public AddMemberCommand(String name) {
        this.name = name;
    }

    //@@author JustinChia1997
    public void setSkill(String fullSkill) {
        skills = Arrays.asList(fullSkill.split("\\s+"));
        LoggerController.logDebug(AddTaskCommand.class, "Added member skill " + skills.get(0));
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        model.addMember(name);
        if (skills != null) {
            for (int i = 0; i < skills.size(); i += 1) {
                model.addMemberSkill(name, skills.get(i));
            }
        }
        model.save();
        return new CommandOutput(FEEDBACK_MESSAGE + name);
    }
}
