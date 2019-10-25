package duke.storage;

import duke.exception.DukeException;
import duke.model.IncomeList;

public interface IncomeListStorage {

    public void saveIncomeList(IncomeList incomeList) throws DukeException;

    public IncomeList loadIncomeList() throws DukeException;
}
