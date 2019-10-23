package help;

import java.util.ArrayList;

public class History {
    private String previousCommand;
    private String nextCommand;
    private int currIndex;
    private ArrayList<String> commandsEntered;
    private boolean flag;
    private boolean flagForFirstPress;

    //@@author {ChenChao19}
    public History() {
        commandsEntered = new ArrayList<>();
        this.flagForFirstPress = true;
        this.flag = true;
    }

    public void setFlagTrue() { //the command before this is up
        flag = true;
    }

    public void setFlagFalse() { //the command before this is down
        flag = false;
    }

    public void addingCommandsEntered(String commands) {
        commandsEntered.add(commands);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public int getMaxIndex() {
        return commandsEntered.size(); //0-based indexing
    }

    public void setCurrIndex() {
        currIndex = commandsEntered.size(); //0-based indexing
    }

    public void setFlagForFirstPress() {
        flagForFirstPress = true;
    }

    public String getPreviousCommand() {
        if (flagForFirstPress) {
            currIndex = getMaxIndex() - 1;
            flagForFirstPress = false;
        } else {
            if (currIndex != 0 && flag) {
                currIndex -= 1;
            }
        }
        previousCommand = commandsEntered.get(currIndex);
        return previousCommand;
    }

    public String getNextCommand() {
        if (currIndex == getMaxIndex()) {
            currIndex = getMaxIndex() - 1;
        } else if (!flag) {
            if (currIndex != getMaxIndex() - 1) {
                currIndex += 1;
            }
        }
        nextCommand = commandsEntered.get(currIndex);
        return nextCommand;
    }
}