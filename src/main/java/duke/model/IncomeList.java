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

/**
 * IncomeList keeps the list of incomes input by the user.
 * It supports a set of basic operations such as adding incomes, removing incomes,
 * and getting the entire list. It inherits from DukeList.
 *
 * It is reflected in BudgetPane for users to keep track of.
 */

public class IncomeList extends DukeList<Income> {

    private static final Logger logger = LogsCenter.getLogger(IncomeList.class);

    private List<Income> internalIncomeList;
    private ObservableList<Income> externalIncomeList;
    private StringProperty totalString;

    /**
     * Constructor for IncomeList
     *
     * @param internalList loaded income list from storage
     */
    public IncomeList(List<Income> internalList) {
        super(internalList, "income");

        externalList = FXCollections.observableArrayList();
        externalIncomeList = FXCollections.unmodifiableObservableList(externalList);
        totalString = new SimpleStringProperty();
        updateExternalList();
    }

    /**
     * Method to update the list upon any changes to income list
     */
    private void updateExternalList() {
        internalIncomeList = internalList;
        externalList.setAll(FXCollections.observableArrayList(internalIncomeList));
        totalString.setValue("Total Income: $" + getTotalExternalAmount());
    }

    /**
     * Adds an income to incomeList
     *
     * @param income
     */
    @Override
    public void add(Income income) {
        super.add(income);
        updateExternalList();
        logger.info("externalList lengths " + externalList.size());
    }

    /**
     * Deletes an income from the incomeList according to its index
     *
     * @param index the index of the item to in {@code externalList}.
     * @throws DukeException if index is not valid
     */
    @Override
    public void remove(int index) throws DukeException {
        super.remove(index);
        updateExternalList();
    }

    /**
     * Clears the entire incomeList
     */
    @Override
    public void clear() {
        super.clear();
        updateExternalList();
    }

    /**
     * Returns list as reflected in BudgetPane
     *
     * @return externalIncomeList incomeList in the form of ObservableList<Income>
     */
    @Override
    public ObservableList<Income> getExternalList() {
        return externalIncomeList;
    }

    /**
     * Returns internal income list
     *
     * @return internalIncomeList incomeList as a List<Income>
     */
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

    /**
     * Returns the total income as a StringProperty
     *
     * @return totalString
     */
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