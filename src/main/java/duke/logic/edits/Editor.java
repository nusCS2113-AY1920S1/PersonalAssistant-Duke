package duke.logic.edits;

import duke.model.ModifiableEditorList;

public class Editor {
    private ModifiableEditorList editorList;

    public Editor(ModifiableEditorList editorList) {
        this.editorList = editorList;
    }

    public int getNearestUp(int index) {
        return 0;
    }

    public int getNearestDown(int index) {
        return 0;
    }

    public int getNearestLeft(int index) {
        return 0;
    }

    public int getNearestRight(int index) {
        return 0;
    }
}
