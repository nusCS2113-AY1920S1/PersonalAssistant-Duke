package duke.models.students;

import java.util.ArrayList;

public interface IProgress {

    ArrayList<Progress> listProgress();

    void addProgress(Progress progress);

    void deleteProgress(int index);



}
