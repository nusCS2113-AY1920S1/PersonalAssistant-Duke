package duke.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OffByOneChecker {
    public static String offByOne(String keyword) {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                                                                      "done", "edit", "todo", "deadline", "event"));
        for (String command : commandList) {
            if (keyword.length() != command.length()) {
                continue;
            }
            int commonCharCount = 0;
            for (int i=0; i<keyword.length(); i++) {
                if (command.charAt(i) == keyword.charAt(i)) {
                    commonCharCount += 1;
                }
            }
            if (commonCharCount == command.length()) {
                return command;
            }
            else if (commonCharCount == command.length()-1) {
                Scanner scanner = new Scanner(System. in);
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
