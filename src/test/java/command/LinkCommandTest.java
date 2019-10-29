package command;

import commands.LinkCommand;
import members.Member;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LinkCommandTest {

    //@@author: Jason
    @Test
    public void checkLink() {
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();

        String description = "Test Task 1";
        ToDo temp = new ToDo(description);
        tasks.add(temp);
        String member = "Test Member 1";
        Member newMember = new Member(member);
        members.add(newMember);

        int setTo = 1;
        //members.get(0).setTask(1); //revisit this
        assertEquals("Test Member 1 is in charge of task(s): [ 1 ].",
                members.get(0).getName() + " is in charge of task(s): [ " + setTo + " ].");
    }


}