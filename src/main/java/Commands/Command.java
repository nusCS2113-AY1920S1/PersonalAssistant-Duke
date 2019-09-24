package Commands;

public abstract class Command {
    protected boolean isExit = false;

    public boolean getIsExit() {
        return isExit;
    }
    public abstract void execute();
}
