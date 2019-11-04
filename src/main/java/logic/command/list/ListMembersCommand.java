package logic.command.list;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Member;
import model.Model;

import java.util.ArrayList;

import gui.Window;

public class ListMembersCommand extends Command {

    public static final String COMMAND_WORD = "members";
    public static final String EMPTY_MEMBERS_LIST = "There are currently no member in project manager";
    //private String arguments;

    //public ListMembersCommand(String arguments) {
        //this.arguments = arguments;
    //}

    @Override
    public CommandOutput execute(Model model) {
        Window.instance.showTaskView(false);
        return new CommandOutput(convertArrayListToText(model.getMemberList()));
    }

    //@@author JustinChia1997

    /**
     * Converts arraylist of members to a displayable text format
     */
    public String convertArrayListToText(ArrayList<Member> members) {
        String finalOutput = "";
        if (members.size() > 0) {
            for (int i = 0; i < members.size(); i += 1) {
                finalOutput += (i + 1) + " : " + "Name: "
                        + members.get(i).getName() + "\n";
            }
        } else {
            finalOutput = EMPTY_MEMBERS_LIST;
        }
        return finalOutput;
    }
}
