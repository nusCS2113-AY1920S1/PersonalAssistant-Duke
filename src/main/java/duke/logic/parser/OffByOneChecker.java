package duke.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * duke.logic.parser.OffByOneChecker class which checks if a keyword is off by one from any keyword in a list of keywords
 */
public class OffByOneChecker {
    /**
     * If command keyword is off by one, verify with user to change command keyword
     *
     * @param keyword the parsed command keyword
     * @return keyword (may not be changed)
     */
    public static String offByOne(String keyword) {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign", "pomo"));
        Scanner scanner = new Scanner(System.in);
        for (String command : commandList) {
            if (keyword.length() != command.length()) {
                continue;
            }
            int commonCharCount = 0;
            for (int i = 0; i < keyword.length(); i++) {
                if (command.charAt(i) == keyword.charAt(i)) {
                    commonCharCount += 1;
                }
            }
            if (commonCharCount == command.length()) {
                return command;
            } else if (commonCharCount == command.length() - 1) {
                System.out.println(String.format("Did you mean %s? (Y/N)", command));
                String input = scanner.nextLine().toUpperCase();
                while (!input.equals("Y") && !input.equals("N")) {
                    System.out.println(String.format("Did you mean %s? Please enter either (Y/N)", command));
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
