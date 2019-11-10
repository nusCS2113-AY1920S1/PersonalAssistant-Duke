package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class IncomeList extends DukeList<Income> {


    private static final Logger logger = LogsCenter.getLogger(IncomeList.class);

    private List<Income> internalIncomeList;
    private ObservableList<Income> externalIncomeList;
    private StringProperty totalString;


    public IncomeList(List<Income> internalList) {
        super(internalList, "income");

        externalList = FXCollections.observableArrayList();
        externalIncomeList = FXCollections.unmodifiableObservableList(externalList);
        totalString = new SimpleStringProperty();
        updateExternalList();
    }

    private void updateExternalList() {
        internalIncomeList = internalList;
        externalList.setAll(FXCollections.observableArrayList(internalIncomeList));
        totalString.setValue("Total Income: $" + getTotalExternalAmount());
    }

    @Override
    public void add(Income income) {
        super.add(income);
        updateExternalList();
        logger.info("externalList lengths " + externalList.size());
    }

    @Override
    public void remove(int index) throws DukeException {
        super.remove(index);
        updateExternalList();
    }

    @Override
    public void clear() {
        super.clear();
        updateExternalList();
    }

    @Override
    public ObservableList<Income> getExternalList() {
        return externalIncomeList;
    }

    @Override
    public List<Income> getInternalList() {
        return internalIncomeList;
    }

    /**
     * Returns an item from its storage string. Although this method is present in the item builders,
     * it is declared here to make it easier to implement (otherwise requires reflection).
     *
     * @param storageString the storage string of the item.
     * @return the item.
     * @throws DukeException if the item could not be created from the storage string.
     */
    public static Income itemFromStorageString(String storageString) throws DukeException {
        return new Income.Builder(storageString).build();
    }

    /**
     * Returns the total amount of money spent on currently visible incomes i.e. those in {@code externalList}.
     *
     * @return BigDecimal of the total amount of money spent on currently visible incomes.
     */
    public BigDecimal getTotalExternalAmount() {
        return externalList.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    StringProperty getTotalString() {
        return totalString;
    }

    @Override
    public void setSortCriteria(String sortCriteria) {
    }

    @Override
    public void setFilterCriteria(String filterCriteria) {
    }

    @Override
    public void setViewScope(String viewScope, int previous) {
    }

    @Override
    public List<Income> sort(List<Income> currentList) {
        return null;
    }

    @Override
    public List<Income> filter(List<Income> currentList) {
        return null;
    }

    @Override
    public List<Income> view(List<Income> currentList) {
        return null;
    }
}