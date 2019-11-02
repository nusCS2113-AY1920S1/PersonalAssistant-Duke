package logic.command.match;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Member;
import model.MemberManager;
import model.Model;
import model.TasksManager;

import java.util.ArrayList;

//@@author JustinChia1997
public class MatchMemberCommand extends Command {
    private String memberName;

    public MatchMemberCommand(String name) {
        memberName = name;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        TasksManager tasksManager = model.getTasksManager();
        MemberManager memberManager = model.getMemberManager();
        ArrayList<Member> memberList = memberManager.getMemberList();


        return new CommandOutput("Member command was called for member name: " + memberName);
    }
}
