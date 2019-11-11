package views;

import controllers.ConsoleInputController;
import util.log.ArchDukeLogger;

import java.util.Scanner;

import static util.constant.ConstantHelper.HELLO_MESSAGE;
import static util.constant.ConstantHelper.HORILINE;
import static util.constant.ConstantHelper.INDENTATION;

public class CLIView {
    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController();
    }

    /**
     * Method to call when View model is started.
     */
    public void start() {
        ArchDukeLogger.logInfo(CLIView.class.getName(), "ArchDuke have started.");
        Scanner sc = new Scanner(System.in);
        boolean isDukeRunning = true;
        System.out.println(String.join("\n", HELLO_MESSAGE));
        while (isDukeRunning) {
            String commandInput = sc.nextLine();
            ArchDukeLogger.logInfo(CLIView.class.getName(), "User input: " + commandInput);
            String[] outputMessage = consoleInputController.onCommandReceived(commandInput);

            if (outputMessage[0].matches("Now managing.*")) {
                consolePrint(outputMessage);

                while (consoleInputController.getIsManagingAProject()) {
                    commandInput = sc.nextLine();
                    String[] projectOutputMessage = consoleInputController.manageProject(commandInput);
                    consolePrint(projectOutputMessage);
                    if (projectOutputMessage[0].matches("Bye.*")) {
                        isDukeRunning = false;
                        break;
                    }
                }
            } else if (outputMessage[0].matches("Bye.*")) {
                isDukeRunning = false;
                consolePrint(outputMessage);
            } else {
                consolePrint(outputMessage);
            }
        }
        System.exit(0);
    }

    /**
     * Prints an indented and formatted message with a top and bottom border.
     * @param lines The lines to be printed in between the border.
     */
    public void consolePrint(String... lines) {
        System.out.println(HORILINE);
        for (String message : lines) {
            System.out.println(INDENTATION + message);
        }
        System.out.println(HORILINE);
    }
}
