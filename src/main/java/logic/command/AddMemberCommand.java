package logic.command;

import model.Model;
import utils.DukeException;

import java.util.Date;

public class AddMemberCommand extends Command {
    public static final String COMMAND_WORD = "member";
    public static final String FEEDBACK_MESSAGE = "You have created a new member: ";
    private String name;

    public AddMemberCommand(String name) {
        this.name = name;
    }
    
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        model.addMember(name);
        return new CommandOutput(FEEDBACK_MESSAGE + name);
    }
}
