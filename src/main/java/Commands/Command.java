package Commands;

public abstract class Command {
    protected boolean isExit = false;
    protected boolean isStart = false;

    public boolean getIsStart() { return isStart; }
    public boolean getIsExit() {
        return isExit;
    }
    public abstract void execute();
}
