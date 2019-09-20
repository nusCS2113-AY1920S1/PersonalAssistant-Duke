public abstract class Command {
    private boolean isExit = false;

    public boolean getIsExit() {
        return isExit;
    }
    public abstract void execute();
}
