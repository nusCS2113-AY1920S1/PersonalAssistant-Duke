package javacake;

import java.util.Stack;

public class ProgressStack {
    private Stack<Integer> currentProgress = new Stack<Integer>();

    public ProgressStack() {

    }

    public void forceToMainList() {
        if (checkProgress() == 0) currentProgress.push(1);
        // only push progress when current screen is at main screen.
        else {
            while (checkProgress() > 1) {
                forceClearProgress();
                // when list command is entered, progress is automatically cleared to main list.
                // checkProgress stack size is forced to 1.
            }
        }

    }

    public void mainListToListIndex1() {
        if (checkProgress() == 1) currentProgress.push(1);
    }

    public void mainListToListIndex2() {
        if (checkProgress() == 1) currentProgress.push(2);
    }

    public void mainListToListIndex3() {
        if (checkProgress() == 1) currentProgress.push(3);
    }

    public void listIndexToMainList() {
        if (checkProgress() == 2) currentProgress.pop();
    }

    public void listIndex3ToSubList1() {
        if (checkProgress() == 2) currentProgress.push(1);
    }

    public void listIndex3ToSubList2() {
        if (checkProgress() == 2) currentProgress.push(1);
    }

    public void listIndex3ToSubList3() {
        if (checkProgress() == 2) currentProgress.push(1);
    }

    public void forceClearProgress() {
        currentProgress.pop();
    }

    /**
     * Checks the current location in the programme stack.
     * @return the size of the stack which indicates the location.
     */
    public int checkProgress() {
        return currentProgress.size();
    }

    /**
     * Checks for the specific branch in the programme stack.
     * Each number indicates a branch based on the index of the list.
     * @return the number of the branch or the index of a particular list.
     */
    public int checkProgressState() {
        return currentProgress.peek();
    }

    /**
     * Used only for BACK command.
     * To check for the previous state stored in the stack.
     * @return the number of the branch or the index of a particular list
     * in the previous state.
     */
    public int checkPreviousState() {
        clearCurrentState();
        return checkProgressState();
    }

    /**
     * Used only for BACK command.
     * To clear the current state of the stack.
     */
    public void clearCurrentState() {
        currentProgress.pop();
    }

}
