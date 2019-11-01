package dolla.action;

import java.util.Stack;

public class StateList {
    private Stack<State> stateList = new Stack<>();

    public void addState(State state) {
        stateList.push(state);
    }

    public State getState() {
        return stateList.pop();
    }

}
