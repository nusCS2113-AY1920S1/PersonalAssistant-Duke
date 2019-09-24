package Commands;

public abstract class Command {
    protected boolean isExit = false;
    protected boolean isStart = false;

    public boolean getIsExit() {
        return isExit;
    }
    public boolean getIsStart() { return isStart; }
    public abstract void execute();
}
