package duke.core;

import duke.command.*;
import duke.patient.Patient;
import duke.relation.EventPatientTask;
import duke.relation.StandardPatientTask;
import duke.task.*;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Parses a Task from a string array.
     *
     * @param userInput The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        userInput = userInput.trim();
        String[] command = userInput.split("\\s+", 3);
        String firstKeyword = command[0].toLowerCase();
        Parser parser = new Parser(userInput);
        switch (firstKeyword) { //change this depending on how string is parsed
            case "add":
                    String secondKeyword = command[1].toLowerCase();
                    if (secondKeyword.equals("patient")){
                        String[] formattedInput = parser.parseAdd();
                        return new AddPatientCommand(formattedInput);
                    }
                    else if (secondKeyword.equals("task")){
                            Task task = new Task(parser.parseAdd()[0]);
                            return new AddStandardTaskCommand(task);
                    }
                    else {
                        throw new DukeException("Invalid format. ");
                    }
            case "assign":
                try {
                    String[] tempCommand = command[1].split("\\s+", 2);
                    if (tempCommand[0].toLowerCase().equals("byid")) {
                        return new AssignTaskToPatientCommand(tempCommand[1]);
                    }

                } catch (Exception e) {
                    throw new DukeException("update command fails. " + e.getMessage());
                }

            case "list":
                try {
                    String[] tempCommand = command[1].split("\\s+");
                    if (tempCommand[0].toLowerCase().equals("patients")){
                        return new ListPatientsCommand();
                    }
                    else if (tempCommand[0].toLowerCase().equals("tasks")){
                        return new ListTasksCommand();
                    }
                    else {
                        throw new Exception("Invalid 'list' command. ");
                    }
                } catch (Exception e) {
                    throw new DukeException("List command fails. " + e.getMessage());
                }
            case "delete":
                try{
                    String[] tempCommand = command[1].split("\\s+", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        try {
                            return new DeletePatientCommand(tempCommand[1]);
                        }catch(Exception e){
                            throw new Exception("Please follow the format 'delete patient #<id>'.");
                        }
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
                } catch(Exception e){
                    throw new DukeException("Delete command fails. " + e.getMessage());
                }
            case "find":
                try{
                    String[] tempCommand = command[1].split("\\s+", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        try {
                            return new FindPatientCommand(tempCommand[1]);
                        }catch(Exception e){
                            throw new Exception("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
                        }
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
                } catch(Exception e){
                    throw new DukeException("Find command fails. " + e.getMessage());
                }
            case "update":
                try {
                    String[] descriptions = command[1].split("\\s+");
                    int targetId = Integer.parseInt(descriptions[1]);
                    String targetInfo = descriptions[2];
                    String updateValue = descriptions[3];

                    return new UpdatePatientCommand(targetId, targetInfo, updateValue);


                } catch (Exception e) {
                    throw new DukeException("update command fails. " + e.getMessage());
                }
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("Could not understand user input.");
        }
    }

}
