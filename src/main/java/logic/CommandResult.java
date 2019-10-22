package logic;

public class CommandResult {
    private String message;
    private boolean isExit;

    public CommandResult(String message) {
        this.message = message;
        this.isExit = false;
    }

    public String getMessage() {
        return message;
    }

    /**
     * simple return if the command is bye.
     * @return if the command is bye
     */
    public boolean isExit() {
        return isExit;
    }

    public void setExit() {
        this.isExit = true;
    }
}
