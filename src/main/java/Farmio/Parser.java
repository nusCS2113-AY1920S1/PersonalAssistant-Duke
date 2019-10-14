package Farmio;

import Commands.*;
import FarmioExceptions.FarmioException;

class Parser {
    static Command parse(String userInput, Farmio.Stage stage) throws FarmioException {
        userInput = userInput.toLowerCase();
        switch (stage) {
            case WELCOME:
                return new CommandMenuStart();
            case MENU_START:
                return parseMenuStart(userInput);
            case TASK_ADD:
                return parseTaskAdd(userInput);
            default:
                //Game should not reach this stage.
                stage = Farmio.Stage.WELCOME;
                throw new FarmioException("Something went wrong! Restarting game.");
        }
//        System.out.println("Received command " + userInput);
//        if (userInput.equals("exit")) {
//            return new ExitCommand();
//        }
//        if (userInput.equals("start")) {
//            return new StartCommand();
//        }
//        if (userInput.equals("test")) {
//            return new TestCommand(ui, storage, farmer);
//        }
//        return new ExitCommand();
    }

    private static Command parseMenuStart(String userInput) throws FarmioException {
        switch (userInput) {
            case "load game":
                return new CommandGameLoad();
            case "new game":
                return new CommandGameNew();
            case "quit game":
                return new CommandGameQuit();
            default:
                throw new FarmioException("Invalid command!");
        }
    }

    private static Command parseTaskAdd(String userInput){
        switch(userInput){
            case "":
                break;
            default:
        }
        return new CommandGameLoad();
    }
}
