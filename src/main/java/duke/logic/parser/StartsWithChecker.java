package duke.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class which checks if a keyword starts with any keyword from a list of keywords
 */
public class StartsWithChecker {
    /**
     * Checks if any command keywords start with the input command keyword
     *
     * @param keyword the parsed command keyword
     * @return keyword (may not be changed)
     */
    public static String checkStartsWithAnyCommand(String keyword) {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign", "pomo"));
        Scanner scanner = new Scanner(System.in);
        for (String command : commandList) {
            if (command.startsWith(keyword)) {
                if (command.equals(keyword)) {
                    return command;
                }
                System.out.println(String.format("Did you mean %s? (Y/N)", command));
                String input = scanner.nextLine().toUpperCase();
                while (!input.equals("Y") && !input.equals("N")) {
                    System.out.println(String.format("Did you mean %s? Please enter only (Y/N)", command));
                    input = scanner.nextLine().toUpperCase();
                }
                if (input.equals("Y")) {
                    return command;
                } else if (input.equals("N")) {
                    return keyword;
                }
            }
        }
        return keyword;
    }
}
