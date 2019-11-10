package command;

//@@author JasonChanWQ

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import logic.parser.ShowCommandParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class ShowTaskDetailsCommandTest {

    @Test
    public void showTaskDetailsCommand_inputIndexNotWithinTaskList_throwsException() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getTaskListSize() - 1; i >= 0; i--)  {
            model.deleteTask(i);
        }
        model.addTask("Complete event poster");
        Command command = ShowCommandParser.parseShowCommand("task 999");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void showTaskDetailsCommandSuccess_checkUneditedTask() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getTaskListSize() - 1; i >= 0; i--)  {
            model.deleteTask(i);
        }
        model.addTask("Complete event poster");
        Command command = ShowCommandParser.parseShowCommand("task 1");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Task: 1\n"
                + "Task Name: Complete event poster ["
                + model.getTaskIsDoneByIdOnList(1) + "]\n"
                + "Description: (Please input a description!)\n"
                + "Deadline: (No deadline assigned!)\n"
                + "Member(s) assigned: (No members assigned!)\n"
                + "Skill(s) required: (No skills assigned!)\n"
                + "Reminder: (No reminder set!)\n", out.getOutputToUser());
    }   //model.getTaskIsDoneByIdOnList(1) is used to get symbol

    @Test
    public void showTaskDetailsCommandSuccess_checkEditedTask() throws DukeException, ParseException {
        Model model = new ModelController();
        for (int i = model.getTaskListSize() - 1; i >= 0; i--)  {
            model.deleteTask(i);
        }
        for (int i = model.getMemberListSize() - 1; i >= 0; i--)  {
            model.deleteMember(i);
        }
        model.addTask("Complete event poster");
        model.addMember("John");

        model.getTaskList().get(0).markAsDone();    //set done
        model.getTasksManager().updateTaskDes(0,"Complete swim meet poster");   //set description
        Date deadline = new SimpleDateFormat("dd/MM/yyyy HHmm").parse("10/10/2020 1015");
        //model.getTaskList().get(0).setTime(deadline);   //set deadline
        model.link(0,"John");   //set members assigned
        model.getTasksManager().addReqSkill("Complete event poster", "Java");   //add skills
        Date reminder = new SimpleDateFormat("dd/MM/yyyy HHmm").parse("10/10/2020 1010");
        model.getTasksManager().addReminder(0,reminder);    //set reminder

        Command command = ShowCommandParser.parseShowCommand("task 1");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Task: 1\n"
                + "Task Name: Complete event poster ["
                + model.getTaskIsDoneByIdOnList(1) + "]\n"
                + "Description: Complete swim meet poster\n"
                + "Deadline: (No deadline assigned!)\n"
                + "Member(s) assigned: [John]\n" + "Skill(s) required: [Java]\n"
                + "Reminder: Sat Oct 10 10:10:00 SGT 2020\n", out.getOutputToUser());
    }   //model.getTaskIsDoneByIdOnList(1) is used to get symbol
}
