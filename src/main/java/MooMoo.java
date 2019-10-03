import MooMoo.*;

public class MooMoo {

    private Ui ui;
    private boolean isExit;

    public MooMoo() {
        ui = new Ui();
        isExit = false;
    }

    private void run() {
        ui.showWelcome();
        while (!isExit) {
            String userInput = ui.readCommand();
            try {
                Command command = Parser.parseCommand(userInput);
                isExit = command.isExit;
            } catch (MooMooException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        ui.showGoodbye();
    }

    public static void main(String[] args){
        new MooMoo().run();
    }

    public String getResponse(String message) {
        return message;
    }
}
