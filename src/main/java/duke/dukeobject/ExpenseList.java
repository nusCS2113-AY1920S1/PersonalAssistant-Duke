package duke.dukeobject;

import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;

import javax.swing.text.View;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseList extends DukeList<Expense> {

    private static enum SortCriteria {
        AMOUNT, TIME, DESCRIPTION;
    }

    private static enum ViewScope {
        DAY, WEEK, MONTH, YEAR, ALL;
    }

    protected SortCriteria sortCriteria;
    protected ViewScope viewScope;
    protected String filterCriteria;

    private static final ViewScope DEFAULT_VIEW_SCOPE = ViewScope.ALL;
    private static final SortCriteria DEFAULT_SORT_CRITERIA = SortCriteria.TIME;

    public ExpenseList(File file) throws DukeException {
        super(file, "expense");
        viewScope = DEFAULT_VIEW_SCOPE;
        sortCriteria = DEFAULT_SORT_CRITERIA;
        externalList = getExternalList();
    }

    /**
     * Updates {@code externalList}, then returns it.
     *
     * @return {@code externalList}.
     */
    @Override
    public List<Expense> getExternalList() {
        List<Expense> filteredSortedList;
        filteredSortedList = filter(sort(view(internalList)));
        externalList = filteredSortedList;
        return externalList;
    }

    /**
     * Sets the sort criteria.
     * Sort criteria include AMOUNT, TIME, DESCRIPTION.
     *
     * @param sortCriteria The String indicating the criteria for sorting.
     * @throws DukeException If the format of sort criteria is incorrect.
     */
    @Override
    public void setSortCriteria(String sortCriteria) throws DukeException {
        try {
            this.sortCriteria = SortCriteria.valueOf(sortCriteria.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SORT_CRITERIA_INVALID, sortCriteria));
        }
    }

    @Override
    public void setFilterCriteria(String filterCriteria) throws DukeException {
        this.filterCriteria = filterCriteria;
    }

    /**
     * Sets the view scope.
     * View scopes incldue DAY, WEEK, MONTH, YEAR, ALL;
     *
     * @param viewScope The string indicating the time scope of displayed list.
     * @throws DukeException If the format of view scope is incorrect.
     */
    @Override
    public void setViewScope(String viewScope) throws DukeException {
        try {
            this.viewScope = ViewScope.valueOf(viewScope.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_VIEW_SCOPE_INVALID, viewScope));
        }
    }

    /**
     * Sorts the given List with the given criteria and returns the sorted List.
     *
     * @param currentList The List going to be sorted.
     * @return The sorted List.
     */
    @Override
    public List<Expense> sort(List<Expense> currentList) {
        switch(sortCriteria) {
            case TIME:
                currentList.sort(Comparator.comparing(Expense::getTime));
                break;
            case AMOUNT:
                currentList.sort(Comparator.comparing(Expense::getAmount));
                break;
            case DESCRIPTION:
                currentList.sort(Comparator.comparing(Expense::getDescription));
                break;
        }
        return currentList;
    }

    /**
     * To be implemented when tags are specified.
     *
     * @param currentList The List going to be filtered.
     * @return The filtered List.
     */
    @Override
    public List<Expense> filter(List<Expense> currentList) {
        return currentList;
    }

    /**
     * Tailors the given List so that only {@code Expense} within the given time scope are preserved.
     * Returns the tailored List.
     *
     * @param currentList The list going to be modified.
     * @return The tailored List.
     */
    @Override
    public List<Expense> view(List<Expense> currentList) {
        List<Expense> viewedList = new ArrayList<Expense>();
        LocalDateTime now = LocalDateTime.now();

        switch(viewScope) {
            case ALL:
                viewedList = currentList;
                break;

            case YEAR:
                viewedList = currentList.stream()
                        .filter(e -> e.getTime().plusYears(1).isAfter(now))
                        .collect(Collectors.toList());
                break;

            case MONTH:
                viewedList = currentList.stream()
                        .filter(e -> e.getTime().plusMonths(1).isAfter(now))
                        .collect(Collectors.toList());
                break;

            case WEEK:
                viewedList = currentList.stream()
                        .filter(e -> e.getTime().plusDays(7).isAfter(now))
                        .collect(Collectors.toList());
                break;
            case DAY:
                viewedList = currentList.stream()
                        .filter(e -> e.getTime().plusDays(1).isAfter(now))
                        .collect(Collectors.toList());
                break;
        }
        return viewedList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expense itemFromStorageString(String storageString) throws DukeException {
        return new Expense.Builder(storageString).build();
    }

    /**
     * Returns the total amount of money spent.
     *
     * @return BigDecimal of the total amount of money spent.
     */
    public BigDecimal getTotalAmount() {
        return internalList.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}