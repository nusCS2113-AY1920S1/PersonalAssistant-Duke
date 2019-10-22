package help;

import java.util.ArrayList;

public class MemorisePreviousFunctions {
    private String previousCommand;
    private String nextCommand;
    private int currIndex;
    private ArrayList<String> CommandsEntered;
    private boolean flag;
    private boolean flagForFirstPress;

    //@@ ChenChao19
    public MemorisePreviousFunctions() {
        CommandsEntered = new ArrayList<>();
        this.flagForFirstPress = true;
        this.flag = true;
    }

    public void setFlagTrue() { //the command before this is up
        flag = true;
    }

    public void setFlagFalse() { //the command before this is down
        flag = false;
    }

    public void addingCommandsEntered(String Commands) {
        CommandsEntered.add(Commands);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public int getMaxIndex() {
        return CommandsEntered.size(); //0-based indexing
    }

    public void setCurrIndex() {
        currIndex = CommandsEntered.size(); //0-based indexing
    }

    public void setFlagForFirstPress() {
        flagForFirstPress = true;
    }

    public String getPreviousCommand() {
        if(flagForFirstPress) {
            currIndex = getMaxIndex() - 1;
            flagForFirstPress = false;
        } else {
            if (currIndex != 0 && flag) {
                currIndex -= 1;
            }
        }
        previousCommand = CommandsEntered.get(currIndex);
        return previousCommand;
    }

    public String getNextCommand() {
        if(currIndex == getMaxIndex()) {
            currIndex = getMaxIndex() - 1;
        } else if (!flag) {
            if(currIndex != getMaxIndex() - 1) {
                currIndex += 1;
            }
        }
        nextCommand = CommandsEntered.get(currIndex);
        return nextCommand;
    }
}