//@@author LongLeCE

package planner.logic.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.logic.command.AddCcaScheduleCommand;
import planner.logic.command.CapCommand;
import planner.logic.command.ClearCommand;
import planner.logic.command.EndCommand;
import planner.logic.command.GradeCommand;
import planner.logic.command.ReminderCommand;
import planner.logic.command.RemoveCommand;
import planner.logic.command.SearchThenAddCommand;
import planner.logic.command.ShowCommand;
import planner.logic.command.SortCommand;
import planner.logic.command.UpdateModuleCommand;
import planner.logic.exceptions.legacy.ModException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ParserTest {

    private Parser parser;

    public ParserTest() {
        parser = new Parser();
    }

    @DisplayName("Invalid Commands Parser Test")
    @Test
    public void invalidCommandInputsShouldReturnNull() throws ModException {
        assertNull(parser.parseCommand("random string"));
        assertNull(parser.parseCommand("-h"));
        assertNull(parser.parseCommand("clear"));
        assertNull(parser.parseCommand(""));
        assertNull(parser.parseCommand(" "));
        assertNull(parser.parseCommand(null));
        assertNull(parser.parseCommand("add cca --begin 3 --end 5"));
        assertNull(parser.parseCommand("add module "));
        assertNull(parser.parseCommand("reminder invalid"));
        assertNull(parser.parseCommand("remove module NotANumber"));
    }

    @DisplayName("Add Command Parser Test")
    @Test
    public void validAddCommandInputShouldReturnAddCommand() throws ModException {
        assertSame(parser.parseCommand("add module CG1111").getClass(), SearchThenAddCommand.class);
        assertSame(parser.parseCommand("add module NotACode").getClass(), SearchThenAddCommand.class);
        assertSame(parser.parseCommand("add cca test --begin 3 --end 5 --dayOfWeek Monday").getClass(),
                SearchThenAddCommand.class);
        assertSame(parser.parseCommand("add module test --begin 3 --end 5 --dayOfWeek Monday").getClass(),
                SearchThenAddCommand.class);
    }

    @DisplayName("Schedule Cca Command Parser Test")
    @Test
    public void validScheduleCcaCommandInputShouldReturnScheduleCcaCommand() throws ModException {
        assertSame(parser.parseCommand("scheduleCca 1 --begin 1 --end 2 --dayOfWeek Fri").getClass(),
                AddCcaScheduleCommand.class);
    }

    @DisplayName("Clear Command Parser Test")
    @Test
    public void validClearCommandInputShouldReturnClearCommand() throws ModException {
        assertSame(parser.parseCommand("clear module").getClass(), ClearCommand.class);
        assertSame(parser.parseCommand("clear cca").getClass(), ClearCommand.class);
        assertSame(parser.parseCommand("clear data").getClass(), ClearCommand.class);
    }

    @DisplayName("Set Password Command Parser Test")
    @Test
    public void validSetPasswordCommandInputShouldReturnSetPasswordCommand() throws ModException {
        //assertSame(parser.parseCommand("passwd test").getClass(), SetPasswordCommand.class);
    }

    @DisplayName("Cap Command Parser Test")
    @Test
    public void validCapCommandInputShouldReturnCapCommand() throws ModException {
        assertSame(parser.parseCommand("cap overall").getClass(), CapCommand.class);
        assertSame(parser.parseCommand("cap list").getClass(), CapCommand.class);
        assertSame(parser.parseCommand("cap module").getClass(), CapCommand.class);
    }

    @DisplayName("End Command Parser Test")
    @Test
    public void validEndCommandInputShouldReturnEndCommand() throws ModException {
        assertSame(parser.parseCommand("bye").getClass(), EndCommand.class);
    }

    @DisplayName("Grade Command Parser Test")
    @Test
    public void validGradeCommandInputShouldReturnGradeCommand() throws ModException {
        assertSame(parser.parseCommand("grade CG1111 A+").getClass(), GradeCommand.class);
        assertSame(parser.parseCommand("grade test grade").getClass(), GradeCommand.class);
    }

    @DisplayName("Reminder Command Parser Test")
    @Test
    public void validReminderCommandInputShouldReturnReminderCommand() throws ModException {
        assertSame(parser.parseCommand("reminder list").getClass(), ReminderCommand.class);
        assertSame(parser.parseCommand("reminder one").getClass(), ReminderCommand.class);
        assertSame(parser.parseCommand("reminder two").getClass(), ReminderCommand.class);
        assertSame(parser.parseCommand("reminder three").getClass(), ReminderCommand.class);
        assertSame(parser.parseCommand("reminder four").getClass(), ReminderCommand.class);
        assertSame(parser.parseCommand("reminder stop").getClass(), ReminderCommand.class);
    }

    @DisplayName("Remove Command Parser Test")
    @Test
    public void validRemoveCommandInputShouldReturnRemoveCommand() throws ModException {
        assertSame(parser.parseCommand("remove module 1000").getClass(), RemoveCommand.class);
        assertSame(parser.parseCommand("remove cca 0").getClass(), RemoveCommand.class);
    }

    @DisplayName("Show Command Parser Test")
    @Test
    public void validShowCommandInputShouldReturnShowCommand() throws ModException {
        assertSame(parser.parseCommand("show cca").getClass(), ShowCommand.class);
        assertSame(parser.parseCommand("show module").getClass(), ShowCommand.class);
        assertSame(parser.parseCommand("show core").getClass(), ShowCommand.class);
        assertSame(parser.parseCommand("show ue").getClass(), ShowCommand.class);
        assertSame(parser.parseCommand("show ge").getClass(), ShowCommand.class);
    }

    @DisplayName("Sort Command Parser Test")
    @Test
    public void validSortCommandInputShouldReturnSortCommand() throws ModException {
        assertSame(parser.parseCommand("sort cca").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time monday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time tuesday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time wednesday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time thursday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time friday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time saturday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort time sunday").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module level").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module grade").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module mc").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module code").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module level --r").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module grade --r").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module --r mc").getClass(), SortCommand.class);
        assertSame(parser.parseCommand("sort module --r code").getClass(), SortCommand.class);
    }

    @DisplayName("Update Command Parser Test")
    @Test
    public void validUpdateCommandInputShouldReturnUpdateCommand() throws ModException {
        assertSame(parser.parseCommand("update module").getClass(), UpdateModuleCommand.class);
    }
}
