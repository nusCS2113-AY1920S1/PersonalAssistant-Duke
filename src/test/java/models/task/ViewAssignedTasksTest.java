package models.task;

import models.data.Project;
import models.member.Member;
import org.junit.jupiter.api.Test;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewAssignedTasksTest {
    Project project = new Project("Infinity_Gauntlet");
    DateTimeHelper dateTimeHelper = new DateTimeHelper();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testViewAssignedTask() {
        String testAssignedTasks = "1. Dillen (Phone: 9999 | Email: dillen@gmail.com)\n"
                + "2. Jerry (Phone: 9999 | Email: jerryn@gmail.com)";
        String testAssignedTasks2 = "1. Dillen (Phone: 9999 | Email: dillen@gmail.com)";
        try {
            Date dueDate = dateTimeHelper.formatDate("19/10/2019");
            ArrayList<String> taskRequirements = new ArrayList<>();
            taskRequirements.add("requirement1");

            project.addTask(new Task("task2",5, dueDate,10, TaskState.TODO, taskRequirements));
            project.addTask(new Task("task1",10, dueDate,10, TaskState.TODO, taskRequirements));

            project.addMember(new Member("Dillen", "9999", "dillen@gmail.com",1));
            project.addMember(new Member("Jerry", "9999", "jerryn@gmail.com",2));

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
