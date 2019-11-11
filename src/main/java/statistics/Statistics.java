package statistics;

import exception.DukeException;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface employs two methods print and loadStatistics and is implemented by both the CohortSize and GraduateEmployment classes
 * The methods are used to store the necessary statistics and likewise, display the bar charts
 */
public interface Statistics {

    public void print(String input);
    public void loadStatistics(List <String>st) throws DukeException;

}

