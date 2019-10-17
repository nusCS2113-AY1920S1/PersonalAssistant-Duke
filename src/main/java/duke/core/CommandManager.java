package duke.core;

import duke.command.*;

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
                        String formattedInput = parser.parseAdd()[0];
                        return new AddStandardTaskCommand(formattedInput);
                    }
                    else {
                        throw new DukeException("Add command fails. ");
                    }
            case "assign":
                    return new AssignTaskToPatientCommand(parser.parseAssign());
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
                    secondKeyword = command[1].toLowerCase();
                    if (secondKeyword.equals("patient")) {
                        String formattedInput = parser.parseDeletePatient();
                        return new DeletePatientCommand(formattedInput);
                    }
                    else if (secondKeyword.equals("task")){
                        return new DeleteTaskCommand(parser.parseDeleteTask());
                    }
                    else if (secondKeyword.equals("patienttask")){

                        return new DeletePatientTaskCommand(command[2]);
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
                } catch(Exception e){
                    throw new DukeException("Delete command fails. " + e.getMessage());
                }
            case "find":
                try{
                    secondKeyword = command[1].toLowerCase();
                    if (secondKeyword.equals("patient")){
                        try {
                            return new FindPatientCommand(command[2]);
                        }catch(Exception e){
                            throw new Exception("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
                        }
                    }
                    else if (secondKeyword.equals("patienttask")){
                        try {
                            return new FindPatientTaskCommand(command[2]);
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
                    secondKeyword = command[1].toLowerCase();
                    if (secondKeyword.equals("patient")){
                        String formattedInput = parser.parseUpdatePatient();
                        return new UpdatePatientCommand(formattedInput);
                    }
                    else if (secondKeyword.equals("task")){
                        String formattedInput = parser.parseUpdateTask();
                        return new UpdateTaskCommand(formattedInput);
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
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
