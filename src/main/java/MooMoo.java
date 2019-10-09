import moomoo.*;

import java.util.ArrayList;

public class MooMoo {

    private Ui ui;
    private boolean isExit;
    private CategoryList categories;

    public MooMoo() {
        ui = new Ui();
        isExit = false;
        categories = new CategoryList();
    }

    private void run() {
        ui.showWelcome();
        while (!isExit) {
            String userInput = ui.readCommand();
            try {
                Command command = Parser.parseCommand(userInput, ui);
                command.execute(ui, categories);
                isExit = command.isExit;
            } catch (MooMooException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        ui.showGoodbye();

    }

    public static void main(String[] args) {
        new MooMoo().run();
    }

    public String getResponse(String message) {
        return message;
    }
}
