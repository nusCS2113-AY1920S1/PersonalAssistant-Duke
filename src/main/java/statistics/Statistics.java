package statistics;

import exception.DukeException;

import java.util.ArrayList;
import java.util.List;

public interface Statistics {

    public void print(String input);
    public void loadStatistics(List <String>st) throws DukeException;

}

