package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UpdateMultipleCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String PROVIDE_INDEX = "Please provide the indexes to be updated.";
    private static final String INVALID_INDEX = "Please enter a valid index.";
    private static final String UPDATE_SINGLE_TASK = "To update a single task, provide the input in this "
            + "format instead: update <pageContent> / <type> <one index in integer form>.";
    private static final String INVALID_UPDATE_FORMAT = "Please use valid update-multiple format:\n"
            + "update-multiple <pageContent> / <type> <indexes separated by ',' without any spacing> <boolean value>\n"
            + "E.g. update-multiple CG1111 / task 1,2,3 true";
    private static final String INCORRECT_TAB = "Please specify a valid tab to carry out update-multiple.\n"
            + "List of tabs available: task, note, file";
    private static final String UPDATED_MULTIPLE_FILES = "Noted. I've updated these files:";
    private static final String UPDATED_MULTIPLE_TASKS = "Noted. I've updated these tasks:";
    private static final String INVALID_BOOLEAN_VALUE = "Please provide a valid boolean value to be updated to.";

    private String type;

    private String moduleCode;
    private String content;
    private String outputMessage = "";

    /**
     * Constructor for initialization of variables to support update of entities.
     * @param pageDataComponents page data components.
     * @param content A string containing the content of the processed user input.
     */
    public UpdateMultipleCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();
    }

    private String updateMultipleFile(List<Integer> finalIndexes, String updateValue, FileList files, String
            outputMessage) throws InputException, DataReadWriteException {
        for (int i = 0; i < finalIndexes.size(); i++) {
            File fileUpdated = files.get(finalIndexes.get(i));
            if (updateValue.equals("true")) {
                files.update(finalIndexes.get(i), true);
            } else if (updateValue.equals("false")) {
                files.update(finalIndexes.get(i), false);
            } else {
                throw new InputException(INVALID_BOOLEAN_VALUE);
            }
            if (i == 0) {
                outputMessage = outputMessage.concat(HORIZONTAL_LINE + "\n" + UPDATED_MULTIPLE_FILES + "\n");
            }
            outputMessage = outputMessage.concat(fileUpdated.toString() + "\n");
        }
        return outputMessage;
    }

    private String updateMultipleTask(List<Integer> finalIndexes, String updateValue, TaskList tasks, String
            outputMessage) throws InputException, DataReadWriteException {
        if (updateValue.equals("false")) {
            for (int i = finalIndexes.size() - 1; i >= 0; i--) {
                tasks.update(finalIndexes.get(i), false);
                if (i == finalIndexes.size() - 1) {
                    outputMessage = outputMessage.concat(HORIZONTAL_LINE + "\n" + UPDATED_MULTIPLE_TASKS + "\n");
                }
                Task taskUpdated = tasks.get(finalIndexes.get(i));
                outputMessage = outputMessage.concat(taskUpdated.toString() + "\n");
            }
        } else if (updateValue.equals("true")) {
            for (int i = 0; i < finalIndexes.size(); i++) {
                tasks.update(finalIndexes.get(i), true);
                if (i == 0) {
                    outputMessage = outputMessage.concat(HORIZONTAL_LINE + "\n" + UPDATED_MULTIPLE_TASKS + "\n");
                }
                Task taskUpdated = tasks.get(finalIndexes.get(i));
                outputMessage = outputMessage.concat(taskUpdated.toString() + "\n");
            }
        } else {
            throw new InputException(INVALID_BOOLEAN_VALUE);
        }
        return outputMessage;
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        int inputSize = content.split(" ").length;

        if (inputSize != 3) {
            throw new InputException(INVALID_UPDATE_FORMAT);
        }
        try {
            String[] splitIndexes = content.split(" ")[1].split(",");
            if ((type.equals("file") || type.equals("note") || type.equals("task")) && (splitIndexes.length == 1)
                    && splitIndexes[0].matches("\\d+")) {
                throw new InputException(UPDATE_SINGLE_TASK);
            } else if ((type.equals("file") || type.equals("note") || type.equals("task"))
                    && (splitIndexes.length == 1)) {
                throw new InputException(PROVIDE_INDEX);
            } else if (!type.equals("file") && !type.equals("note") && !type.equals("task")) {
                throw new InputException(INCORRECT_TAB);
            }
            List<Integer> finalIndexes = new ArrayList<>();
            for (String convert : splitIndexes) {
                finalIndexes.add(Integer.parseInt(convert) - 1);
            }
            finalIndexes.sort(Collections.reverseOrder());
            String updateValue = content.split(" ")[2].toLowerCase();
            switch (type) {
            case "file":
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    outputMessage = updateMultipleFile(finalIndexes, updateValue, files, outputMessage).concat(
                                    HORIZONTAL_LINE);
                    return outputMessage;
                } else {
                    return NON_EXISTENT_MODULE;
                }

            case "task":
                checkIfOnModulePage(moduleCode);
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    outputMessage = updateMultipleTask(finalIndexes, updateValue, tasks, outputMessage).concat(
                                    HORIZONTAL_LINE);
                    return outputMessage;
                } else {
                    return NON_EXISTENT_MODULE;
                }

            default:
                throw new InputException(INVALID_UPDATE_FORMAT);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new InputException(INVALID_INDEX);
        }
    }
}