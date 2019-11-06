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
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

//@@author JustinChia1997
public class MatchMemberCommand extends Command {
    private String memberName;

    public MatchMemberCommand(String name) {
        memberName = name;
    }

    /**
     * Creates a key value map of matched members and number of skills they were matched too
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        ArrayList<String> matchedTaskNames = new ArrayList<>();
        TasksManager tasksManager = model.getTasksManager();
        MemberManager memberManager = model.getMemberManager();
        ArrayList<Task> taskList = tasksManager.getTaskList();
        HashMap<String, Integer> matchedTasks = new HashMap<String, Integer>();
        Member member = memberManager.getMemberByName(memberName);
        if (member == null) {
            throw new DukeException("member does not exist");
        }
        ArrayList<String> memberSkillList = member.getSkillList();
        if (memberSkillList == null) {
            throw new DukeException("member does not have any skills attached");
        }
        for (int i = 0; i < taskList.size(); i += 1) {
            getTaskWithSkill(taskList.get(i), memberSkillList, matchedTasks);
        }
        ArrayList<String> sorted = sortMatchedTasks(matchedTasks);


        String userOutput = "These are the tasks that requires skills of " + memberName + " : \n";
        for (int i = 0; i < sorted.size(); i += 1) {
            userOutput += (i + 1) + ": " + sorted.get(i) + "\n";
        }
        if (sorted.size() == 0) {
            userOutput = "No matching task that requires these members skills";
        }

        return new CommandOutput(userOutput);
    }

    private void getTaskWithSkill(Task task, ArrayList<String> memberSkillList, HashMap<String, Integer> matchedTasks) {
        for (int j = 0; j < memberSkillList.size(); j += 1) {
            if (task.hasSkill(memberSkillList.get(j))) {
                if (matchedTasks.containsKey(task.getName())) {
                    int numOfMatched = matchedTasks.get(task.getName());
                    numOfMatched += 1;
                    matchedTasks.replace(task.getName(), numOfMatched);
                } else {
                    matchedTasks.put(task.getName(), 1);
                }
                LoggerController.logDebug(this.getClass(), "CHECK FOR NEW MATCH " +
                        task.getName() + " " + matchedTasks.get(task.getName()));
            }
        }
    }

    private ArrayList<String> sortMatchedTasks(HashMap<String, Integer> matchedTasks) {
        boolean swapped = true;
        ArrayList<String> sortedMatchedTasks = new ArrayList<>(matchedTasks.keySet());
        while(swapped) {
            swapped = false;
            for(int i=0; i < sortedMatchedTasks.size()-1; i+=1) {
                if(matchedTasks.get(sortedMatchedTasks.get(i)) < matchedTasks.get(sortedMatchedTasks.get(i+1))){
                    String temp = sortedMatchedTasks.get(i);
                    Collections.swap(sortedMatchedTasks, i, i+1);
                    swapped = true;
                }
            }
        }
        return sortedMatchedTasks;
    }

}


