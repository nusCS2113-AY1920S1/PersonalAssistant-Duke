package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.exceptions.MenuExceptions;
import com.algosenpai.app.logic.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuCommand extends Command {

    private static HashMap<String, String> commandExplanation = null;

    /**
     * Initializes command to show available commands.
     * @param inputs user input.
     */
    public MenuCommand(ArrayList<String> inputs) {
        super(inputs);
        if (commandExplanation == null) {
            commandExplanation = new HashMap<>();
            try {
                InputStream is = getClass().getResourceAsStream("/data/commandList.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder input = new StringBuilder();
                while (true) {
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    input.append(line).append("\n");
                }
                String[] arr = input.toString().split("-\n");
                for (String s : arr) {
                    String[] temp = s.split("\n", 2);
                    commandExplanation.put(temp[0], temp[1]);
                }
            } catch (IOException e) {
                System.out.println("Cant find");
            }
        }
    }

    @Override
    public String execute() {
        if (inputs.size() == 1) {
            return commandExplanation.get("Introduction");
        } else {
            try {
                MenuExceptions.checkInput(inputs);
                return commandExplanation.getOrDefault(inputs.get(1), "Error there is no such command,"
                        + " enter `menu` to get the list of available commands.");
            } catch (MenuExceptions e) {
                return e.getMessage();
            }
        }
    }
}
