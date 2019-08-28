package controllers;

import exceptions.DukeException;
import models.Deadline;
import models.Event;
import models.ITask;
import models.ToDos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskFactory {
    /**
     * Factory class responsible for creation of objects based on interface.
     *
     * @param input : Command typed into CLI
     * @return : returns an models.ITask based on command typed into CLI
     * @throws DukeException : when command entered does not match existing Tasks
     */
    public ITask createTask(String input) throws DukeException {
        String[] allArgs = input.split(" ");
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        String tempString;
        String[] parsedStrings;
        switch (allArgs[0]) {
        case "todo":
            listArgs.remove(0);
            if (listArgs.size() == 0) {
                throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
            }
            String description = String.join(" ", listArgs);
            return new ToDos(description);
        case "deadline":
            listArgs.remove(0); // Remove "deadline"
            tempString = String.join(" ", listArgs);
            parsedStrings = tempString.split(" /by ");
            return new Deadline(parsedStrings[0], parsedStrings[1]);
        case "event":
            listArgs.remove(0); // Remove "event"
            tempString = String.join(" ", listArgs);
            parsedStrings = tempString.split(" /at ");
            return new Event(parsedStrings[0], parsedStrings[1]);
        default:
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
