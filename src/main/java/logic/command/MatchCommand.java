package logic.command;

import common.DukeException;
import common.LoggerController;
import model.MemberManager;
import model.Member;
import model.Model;
import model.Task;
import model.TasksManager;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MatchCommand extends Command {
    private String taskName;
    public MatchCommand(String name) {
        taskName = name;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        TasksManager tasksManager = model.getTasksManager();
        MemberManager memberManager = model.getMemberManager();
        ArrayList<Member> memberList = memberManager.getMemberList();
        Task task = tasksManager.getTaskByName(taskName);
        ArrayList<String> reqSkills = task.getReqSkills();
        ArrayList<String> matchedMembers = new ArrayList<>();
        for(int i=0; i< memberList.size(); i+=1){
            if(memberList.get(i).hasSkill("fly")){
                LoggerController.logDebug(this.getClass(), memberList.get(i).getName());
                matchedMembers.add(memberList.get(i).getName());
            }
        }
        String userOutput = "";
        for(int i=0; i < matchedMembers.size(); i+=1){
            userOutput +=String.valueOf(i+1) +": " + matchedMembers.get(i) + "\n";
        }
        return new CommandOutput(userOutput);
    }
}
