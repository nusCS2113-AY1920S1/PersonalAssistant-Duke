package help;

import java.util.ArrayList;

public class MemorisePreviousFunctions {
    private String previousCommand;
    private String nextCommand;
    private int currIndex;
    private ArrayList<String> CommandsEntered;

    public MemorisePreviousFunctions() {
        CommandsEntered = new ArrayList<>();
    }

    public void addingCommandsEntered(String Commands) {
        CommandsEntered.add(Commands);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public int getMaxIndex() {
        return CommandsEntered.size();
    }

    public void setCurrIndex() {
        currIndex = CommandsEntered.size();
    }

    public String getPreviousCommand() {
        previousCommand = CommandsEntered.get(currIndex);
        currIndex -= 1;
        return  previousCommand;
    }

    public String getNextCommand() {
        currIndex += 1;
        nextCommand = CommandsEntered.get(currIndex);
        return nextCommand;
    }
}
