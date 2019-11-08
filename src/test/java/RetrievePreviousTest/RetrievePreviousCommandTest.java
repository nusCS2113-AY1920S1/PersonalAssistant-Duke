package RetrievePreviousTest;

import Commands.Command;
import Commands.RetrievePreviousCommand;
import Commands.ShowPreviousCommand;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import Parser.FindFreeTimesParse;
import Parser.ShowPreviousParse;
import Parser.WeekParse;
import StubClasses.StorageStub;
import Tasks.TaskList;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RetrievePreviousCommandTest {
   private static ArrayList<String> previousInputList;
   private static String userInputWithInvalidNumber;
   private static String userInputToGetFromEmptyPreviousInputList;
   private static String userInputWithValidNumber;
   private static String userInputToGetFromNonEmptyPreviousInputList;
   private static String userInputWithoutInteger;
   private static String userInputWithString;
   private TaskList events = new TaskList();
   private TaskList deadlines = new TaskList();
   private StorageStub storageStub = new StorageStub();
   private UserInteraction ui = new UserInteraction();

   @BeforeAll
   public static void setAllVariables() {
       userInputWithInvalidNumber = "retrieve/previous 3";
       userInputToGetFromEmptyPreviousInputList = "retrieve/previous 1";
       userInputWithValidNumber = "retrieve/previous 2";
       userInputToGetFromNonEmptyPreviousInputList = "retrieve/previous 1";
       userInputWithoutInteger = "retrieve/previous";
       userInputWithString = "retrieve/previous abc";
   }

   @Before
   public void runWeekCommand() {
       String actual = "No error";
       String validUserInputWithDuration = "show/week 3";
       Command command = null;
       try {
           command = new WeekParse(validUserInputWithDuration).parse();
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (DukeInvalidFormatException e) {
           actual = e.getMessage();
       } catch (Exception e) {
           e.printStackTrace();
       }
       assertNotNull(command, actual);
   }

   @Before
   public void setRetrievedFreeTimesList() {
       String actual = "No error";
       String validUserInputWithDuration = "find/time 3 hours";
       Command command = null;
       try {
           command = new FindFreeTimesParse(validUserInputWithDuration).parse();
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (DukeInvalidFormatException e) {
           actual = e.getMessage();
       } catch (Exception e) {
           e.printStackTrace();
       }
       assertNotNull(command, actual);
   }

   @Before
   public void showPreviousCommandList() {
       //To facilitate show previous
       setRetrievedFreeTimesList();
       runWeekCommand();
       setRetrievedFreeTimesList();
       runWeekCommand();
       String actual = "No error";
       String validUserInput = "show/previous 2";
       Command command = null;
       try {
           command = new ShowPreviousParse(validUserInput).parse();
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (DukeInvalidFormatException e) {
           actual = e.getMessage();
       } catch (Exception e) {
           e.printStackTrace();
       }
       assertNotNull(command, actual);
   }

   @Test
   public void retrievePreviousCommandTestWithInvalidNUmber() {
       //To facilitate retrieve previous
       showPreviousCommandList();
       runWeekCommand();

       previousInputList = ShowPreviousCommand.getOutputList();
       int sizeOfList = previousInputList.size();
       Command command = new RetrievePreviousCommand(userInputWithInvalidNumber);
       String expected = "There are only " + sizeOfList + " of previous commands." +
               "Please enter a valid number less than or equal to " + sizeOfList + " .";
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }

   @Test
   public void retrievePreviousCommandTestWithEmptyPreviousInputList() {
       Command command = new RetrievePreviousCommand(userInputToGetFromEmptyPreviousInputList);
       String expected = "You did not enter Show Previous Command yet. \n" +
               "Format: show previous <num> or show previous <type> <num>";
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }

   @Test
   public void retrievePreviousCommand_userInputWithoutInteger_throwsDukeInvalidCommandException() {
       Command command = new RetrievePreviousCommand(userInputWithoutInteger);
       String expected = "<x> cannot be empty. Please enter the valid command as retrieve/previous <x>, "
               + "where x is an integer.";
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }

   @Test
   public void retrievePreviousCommand_userInputWithString_throwsDukeInvalidCommandException() {
       Command command = new RetrievePreviousCommand(userInputWithString);
       String expected = "Unable to parse string. retrieve/previous <x>, where x must be an integer and not string.";
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }

   @Test
   public void retrievePreviousCommandTestWithValidNumber() {
       //To facilitate retrieve previous
       showPreviousCommandList();
       runWeekCommand();

       Command command = new RetrievePreviousCommand(userInputWithValidNumber);
       previousInputList = ShowPreviousCommand.getOutputList();
       int index = 1;
       String chosenInput = previousInputList.get(index);
       String expected = "Your chosen previous input is: \n" + chosenInput;
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }

   @Test
   public void retrievePreviousCommandTestWithNonEmptyList() {
       //To facilitate retrieve previous
       showPreviousCommandList();
       runWeekCommand();

       //For actual testing
       Command command = new RetrievePreviousCommand(userInputToGetFromNonEmptyPreviousInputList);
       previousInputList = ShowPreviousCommand.getOutputList();
       int index = 0;
       String chosenInput = previousInputList.get(index);
       String expected = ui.showChosenPreviousChoice(chosenInput);
       String actual = "";
       try {
           actual = command.execute(events, deadlines, ui, storageStub);
       } catch (Exception e) {
           actual = e.getMessage();
       }
       assertEquals(expected, actual);
   }
}

