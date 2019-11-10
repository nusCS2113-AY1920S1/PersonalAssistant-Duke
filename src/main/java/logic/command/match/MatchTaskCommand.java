package logic.command.match;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.MemberManager;
import model.Member;
import model.Model;
import model.Task;
import model.TasksManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MatchTaskCommand extends Command {
    private int taskID;

    public MatchTaskCommand(int name) {
        taskID = name;
    }

    private final String NO_SUCH_TASK_MESSAGE = "Could not find such a task!";
    private final String USER_OUTPUT_STARTING = "These are the members with the relevant skills: \n";
    private final String NO_MATCHING_MEMBER = "No matching members who have the skills or task has required no " +
            "skills assigned";
    private final String TASK_HAS_NO_SKILL = "Task does not have any required skills";

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        TasksManager tasksManager = model.getTasksManager();
        MemberManager memberManager = model.getMemberManager();
        ArrayList<Member> memberList = memberManager.getMemberList();
        HashMap<String, Integer> matchedMembers = new HashMap<>();
        Task task = tasksManager.getTaskById(taskID - 1);
        if (task == null) {
            throw new DukeException(NO_SUCH_TASK_MESSAGE); //TODO consider using task name instead?
        }
        ArrayList<String> reqSkills = task.getReqSkills();
        if (reqSkills == null) {
            throw new DukeException(TASK_HAS_NO_SKILL);
        }

        for (int i = 0; i < memberList.size(); i += 1) {
            getMemberWithSkill(memberList.get(i), reqSkills, matchedMembers);
        }

        ArrayList<String> sortedMembers = sortMatchedMembers(matchedMembers);
        String userOutput = USER_OUTPUT_STARTING;
        for (int i = 0; i < sortedMembers.size(); i += 1) {
            userOutput += (i + 1) + ": " + sortedMembers.get(i) + "\n";
        }
        if (sortedMembers.size() == 0) {
            userOutput = NO_MATCHING_MEMBER;
        }
        return new CommandOutput(userOutput);
    }

    /**
     * updates the members correctly from the hashmap
     * */
    private void getMemberWithSkill(Member member, ArrayList<String> reqSkills,
                                      HashMap<String, Integer> matchedMembers) {
        for (String reqSkill : reqSkills) {
            if (member.hasSkill(reqSkill)) {
                if (matchedMembers.containsKey(member.getName())) {
                    int numMatch = matchedMembers.get(member.getName());
                    numMatch += 1;
                    matchedMembers.replace(member.getName(), numMatch);
                } else {
                    matchedMembers.put(member.getName(), 1);
                }
            }
        }
    }

    /**
     * Does bubble sorting on the matchedTask to find most matched member
     * */
    private ArrayList<String> sortMatchedMembers(HashMap<String, Integer> matchedMembers) {
        boolean swapped = true;
        ArrayList<String> sortedMatchedMembers = new ArrayList<>(matchedMembers.keySet());
        while (swapped) {
            swapped = false;
            for (int i = 0; i < sortedMatchedMembers.size() - 1; i += 1) {
                if (matchedMembers.get(sortedMatchedMembers.get(i)) < matchedMembers.get(sortedMatchedMembers.get(i + 1))) {
                    Collections.swap(sortedMatchedMembers, i, i + 1);
                    swapped = true;
                }
            }
        }
        return sortedMatchedMembers;
    }
}
