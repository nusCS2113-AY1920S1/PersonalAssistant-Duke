package duke.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StartsWithChecker {
    public static String checkStartsWithAnyCommand(String keyword) {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                                                                      "done", "edit", "task"));
        Scanner scanner = new Scanner(System. in);
        for (String command : commandList) {
            if (command.startsWith(keyword)) {
                if (command.equals(keyword)) {
                    return command;
                }
                System.out.println(String.format("Did you mean %s? (Y/N)", command));
                String input = scanner.nextLine().toUpperCase();
                while (!input.equals("Y") && !input.equals("N")) {
                    System.out.println(String.format("Did you mean %s? Please enter anything (Y/N)", command));
                    input = scanner.nextLine();
                }
                if (input.equals("Y")) {
                    return command;
                }
                else if (input.equals("N")){
                    return keyword;
                }
            }
        }
        return keyword;
    }
}
