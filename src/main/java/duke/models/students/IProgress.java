package duke.models.students;

import java.util.ArrayList;

public interface IProgress {

    ArrayList<Student> listProgress();

    void addProgress();

    void deleteProgress(int index);

}
