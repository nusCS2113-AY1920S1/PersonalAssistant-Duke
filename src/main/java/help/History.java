package help;

import java.util.ArrayList;

/**
 * This class is created to memorise previous commands type in by the user.
 */
public class History {
    private int currIndex;
    private ArrayList<String> commandsEntered;
    private boolean flag;
    private boolean flagForFirstPress;

    //@@author ChenChao19
    /**
     * This is the constructor for recording previous commands type in by the user.
     */
    public History() {
        commandsEntered = new ArrayList<>();
        this.flagForFirstPress = true;
        this.flag = true;
    }

    public ArrayList getCommandsEntered() {
        return commandsEntered;
    }

    public void setFlagTrue() { //the command before this is up
        flag = true;
    }

    public void setFlagFalse() { //the command before this is down
        flag = false;
    }

    public boolean getFlag() {
        return flag;
    }

    public void addingCommandsEntered(String commands) {
        commandsEntered.add(commands);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public void setCurrIndex() {
        currIndex = commandsEntered.size(); //0-based indexing
    }

    public int getMaxIndex() {
        return commandsEntered.size(); //0-based indexing
    }

    public void setFlagForFirstPress() {
        flagForFirstPress = true;
    }

    public boolean getFlagForFirstPress() {
        return flagForFirstPress;
    }

    /**
     * This method gets the previous command that the user type into the TextField
     * userInput. It is called when the user pressed the up button on the keyboard
     * and triggers a key event.
     * @return The previous command entered by the user.
     */
    public String getPreviousCommand() {
        if (flagForFirstPress) {
            currIndex = getMaxIndex() - 1;
            flagForFirstPress = false;
        } else {
            if (currIndex != 0 && flag) {
                currIndex -= 1;
            }
        }
        return commandsEntered.get(currIndex);
    }

    /**
     * This method gets the next command that the user type into the TextField
     * userInput. It is called when the user pressed the down button on the keyboard
     * and triggers a key event.
     * @return The next command entered by the user.
     */
    public String getNextCommand() {
        if (currIndex == getMaxIndex()) {
            currIndex = getMaxIndex() - 1;
        } else if (!flag) {
            if (currIndex != getMaxIndex() - 1) {
                currIndex += 1;
            }
        }
        return commandsEntered.get(currIndex);
    }
}