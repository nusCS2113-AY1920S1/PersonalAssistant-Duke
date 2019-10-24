package modeltests.task;

import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewAssignedTasksTest {
    private Project project;
    private DateTimeHelper dateTimeHelper;

    ViewAssignedTasksTest() {
        this.project = new Project("Infinity_Gauntlet");
        this.dateTimeHelper = new DateTimeHelper();
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testViewAssignedTask() {
        String testAssignedTasks = "1. Dillen (Phone: 9999 | Email: dillen@gmail.com | Role: member)\n"
                + "2. Jerry (Phone: 9999 | Email: jerryn@gmail.com | Role: member)";
        String testAssignedTasks2 = "1. Dillen (Phone: 9999 | Email: dillen@gmail.com | Role: member)";
        try {
            Date dueDate = dateTimeHelper.formatDate("19/10/2019");
            ArrayList<String> taskRequirements = new ArrayList<>();
            taskRequirements.add("requirement1");

            project.addTask(new Task("task2",5, dueDate,10, TaskState.TODO, taskRequirements));
            project.addTask(new Task("task1",10, dueDate,10, TaskState.TODO, taskRequirements));

            project.addMember(new Member("Dillen", "9999", "dillen@gmail.com",1, "member"));
            project.addMember(new Member("Jerry", "9999", "jerryn@gmail.com",2, "member"));

            project.getTask(1).assignMember(project.getMembers().getMember(1));
            project.getTask(1).assignMember(project.getMembers().getMember(2));
            project.getTask(2).assignMember(project.getMembers().getMember(1));

            assertEquals(project.getTask(1).getAssignedMembers().getAllMemberDetails().get(0) + "\n"
                    + project.getTask(1).getAssignedMembers().getAllMemberDetails().get(1),testAssignedTasks);
            assertEquals(project.getTask(2).getAssignedMembers().getAllMemberDetails().get(0),testAssignedTasks2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
