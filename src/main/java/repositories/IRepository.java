package repositories;

import java.util.ArrayList;
import models.data.IProject;

public interface IRepository<T> {
    ArrayList<T> getAll();

    boolean addToRepo(String input);

    void deleteItem();

    IProject getProject(int projectNumber);
}
