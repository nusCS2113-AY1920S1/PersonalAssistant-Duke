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

    public void listIndex1ToMainList() {
        if (checkProgress() == 2) currentProgress.pop();
    }

    public void listIndex2ToMainList() {
        if (checkProgress() == 2) currentProgress.pop();
    }

    public void listIndex3ToMainList() {
        if (checkProgress() == 2) currentProgress.pop();
    }

    public void mainListToMainScreen() {
        if (checkProgress() == 1) currentProgress.pop();
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

    public int checkProgress() {
        return currentProgress.size();
    }

}
