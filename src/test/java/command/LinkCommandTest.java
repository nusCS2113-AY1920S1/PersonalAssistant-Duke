package command;

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
        ArrayList<Task> tasks;
        tasks = new ArrayList<Task>();
        ArrayList<Member> members;
        members = new ArrayList<Member>();

        String description = "This is a task for checkLink";
        ToDo temp = new ToDo(description);
        tasks.add(temp);
        String member = "This is a member's name for checkLink";
        Member newMember = new Member(member);
        members.add(newMember);

        int setTo = 1;


        //members.get(0).setTask(1); //revisit this
        assertEquals("This is a member's name for checkLink is in charge of task(s): [ 1 ].",
                members.get(0).getName() + " is in charge of task(s): [ " + setTo + " ].");
    }


}