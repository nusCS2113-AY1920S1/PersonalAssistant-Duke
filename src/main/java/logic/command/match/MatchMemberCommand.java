package logic.command.match;

import common.DukeException;
import common.LoggerController;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Task;
import model.Model;
import model.TasksManager;
import model.MemberManager;
import model.Member;


import java.util.ArrayList;

//@@author JustinChia1997
public class MatchMemberCommand extends Command {
    private String memberName;

    public MatchMemberCommand(String name) {
        memberName = name;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        ArrayList<String> matchedTaskNames = new ArrayList<>();
        TasksManager tasksManager = model.getTasksManager();
        MemberManager memberManager = model.getMemberManager();
        ArrayList<Task> taskList = tasksManager.getTaskList();
        Member member = memberManager.getMemberByName(memberName);
        if (member == null) {
            throw new DukeException("member does not exist");
        }
        ArrayList<String> memberSkillList = member.getSkillList();
        if (memberSkillList == null) {
            throw new DukeException("member does not have any skills attached");
        }
        for (int i = 0; i < taskList.size(); i += 1) {
            String matchedTask = "";
            matchedTask = matchedMember(taskList.get(i), memberSkillList);
            if (matchedTask != null) {
                LoggerController.logDebug(this.getClass(), "matched Task");
                matchedTaskNames.add(matchedTask);
            }
        }

        String userOutput = "These are the tasks that requires skills of " + memberName + " : \n";
        for (int i = 0; i < matchedTaskNames.size(); i += 1) {
            userOutput += (i + 1) + ": " + matchedTaskNames.get(i) + "\n";
        }
        if (matchedTaskNames.size() == 0) {
            userOutput = "No matching task that requires these members skills";
        }

        return new CommandOutput(userOutput);
    }

    private String matchedMember(Task task, ArrayList<String> memberSkillList) {
        for (int j = 0; j < memberSkillList.size(); j += 1) {
            if (task.hasSkill(memberSkillList.get(j))) {
                return task.getName();
            }
        }
        return null;
    }

}
