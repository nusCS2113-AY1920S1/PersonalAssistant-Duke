package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IncomeList extends DukeList<Income> {


    private static final Logger logger = LogsCenter.getLogger(IncomeList.class);

    private enum SortCriteria {
        AMOUNT(Comparator.comparing(Income::getAmount)),
        DESCRIPTION(Comparator.comparing(Income::getDescription)),
        TIME(Comparator.comparing(Income::getTime));


        private Comparator<Income> comparator;

        SortCriteria(Comparator<Income> comparator) {
            this.comparator = comparator;
        }
    }

    public enum ViewScopeName {
        DAY, WEEK, MONTH, YEAR, ALL;
    }

    public class ViewScope {
        private int viewScopeNumber;
        private ViewScopeName viewScopeName;

        public ViewScope(String viewScopeName, int viewScopeNumber) throws DukeException {
            this.viewScopeNumber = viewScopeNumber;
            try {
                this.viewScopeName = ViewScopeName.valueOf(viewScopeName.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new DukeException(String.format(
                        DukeException.MESSAGE_EXPENSE_VIEW_SCOPE_NAME_INVALID, viewScopeName));
            }
        }

        public ViewScope(ViewScopeName viewScopeName) {
            this.viewScopeNumber = 0;
            this.viewScopeName = viewScopeName;
        }


        private List<Income> dayView(List<Income> currentList) {
            return currentList.stream()
                    .filter(e -> {
                        LocalDate dateOfIncome = e.getTime().toLocalDate();
                        LocalDate current = LocalDate.now().minusDays(viewScopeNumber);
                        return dateOfIncome.equals(current);
                    })
                    .collect(Collectors.toList());
        }

        private List<Income> weekView(List<Income> currentList) {
            return currentList.stream()
                    .filter(e -> {
                        int dayOfWeek = e.getTime().getDayOfWeek().getValue();
                        LocalDate start = e.getTime().minusDays(dayOfWeek - 1).toLocalDate(); // Sunday of week of income.
                        LocalDate end = e.getTime().plusDays(7 - dayOfWeek).toLocalDate(); // Monday of week of income.
                        LocalDate current = LocalDate.now().minusWeeks(viewScopeNumber);

                        return (current.equals(end) || current.equals(start)
                                || (current.isAfter(start) && current.isBefore(end)));
                    })
                    .collect(Collectors.toList());
        }

        private List<Income> monthView(List<Income> currentList) {
            return currentList.stream()
                    .filter(e -> {
                        LocalDate dateOfIncome = e.getTime().toLocalDate();
                        LocalDate current = LocalDate.now().minusMonths(viewScopeNumber);
                        boolean isSameYear = dateOfIncome.getYear() == current.getYear();
                        boolean isSameMonth = dateOfIncome.getMonth().equals(current.getMonth());
                        return (isSameYear && isSameMonth);
                    })
                    .collect(Collectors.toList());
        }

        private List<Income> yearView(List<Income> currentList) {
            return currentList.stream()
                    .filter(e -> {
                        LocalDate dateOfIncome = e.getTime().toLocalDate();
                        LocalDate current = LocalDate.now().minusYears(viewScopeNumber);
                        return dateOfIncome.getYear() == current.getYear();
                    })
                    .collect(Collectors.toList());
        }

        /**
         * Returns a filtered list based on the view scope.
         * @param currentList List of Incomes we want to filter down
         * @return the filtered List of Income
         */
        public List<Income> view(List<Income> currentList) {
            switch (viewScopeName) {
                case DAY:
                    return dayView(currentList);

                case WEEK:
                    return weekView(currentList);

                case MONTH:
                    return monthView(currentList);

                case YEAR:
                    return yearView(currentList);

                default: // case ALL:
                    return currentList; // the viewScope here is ALL.
            }
        }

        public ViewScopeName getViewScopeName() {
            return viewScopeName;
        }
    }

    private SortCriteria sortCriteria;
    private ViewScope viewScope;
    private String filterCriteria;

    private List<Income> filteredSortedViewedList;
    private ObservableList<Income> internalFinalList;
    private ObservableList<Income> externalFinalList;

    /**
     * Creates a new income list using a file for storage.
     *
     * @throws DukeException if the file could not be loaded from, or created.
     */
    /*
    public IncomeList(File file) throws DukeException {
        super(file, "income");
        viewScope = new ViewScope(ViewScopeName.ALL);
        sortCriteria = SortCriteria.TIME;
        externalList = getExternalList();
    }

     */

    public IncomeList(List<Income> internalList) {
        super(internalList, "income");
        viewScope = new ViewScope(ViewScopeName.ALL);
        sortCriteria = SortCriteria.TIME;
        externalList = FXCollections.observableArrayList();
        externalFinalList = FXCollections.unmodifiableObservableList(externalList);
        updateExternalList();
    }

    private void updateExternalList() {
        filteredSortedViewedList = filter(sort(view(internalList)));
        internalFinalList = FXCollections.observableArrayList(filteredSortedViewedList);
        externalList.setAll(internalFinalList);
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

    /**
     * Updates {@code externalList}, then returns it.
     *
     * @return {@code externalList}.
     */
    @Override
    public ObservableList<Income> getExternalList() {
        return externalFinalList;
    }

    @Override
    public List<Income> getInternalList() {
        return internalList;
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
            throw new DukeException(String.format(DukeException.MESSAGE_EXPENSE_SORT_CRITERIA_INVALID, sortCriteria));
        }
        updateExternalList();
    }

    @Override
    public void setFilterCriteria(String filterCriteria) throws DukeException {
        this.filterCriteria = filterCriteria;
        updateExternalList();
    }

    public SortCriteria getSortCriteria() {
        return sortCriteria;
    }

    public String getFilterCriteria() {
        return filterCriteria;
    }

    public ViewScope getViewScope() {
        return viewScope;
    }

    /**
     * Sets the view scope.
     * View scopes include DAY, WEEK, MONTH, YEAR, ALL;
     *
     * @param viewScopeName The string indicating the time scope of displayed list.
     * @throws DukeException If the format of view scope is incorrect.
     */
    @Override
    public void setViewScope(String viewScopeName, int viewScopeNumber) throws DukeException {
        this.viewScope = new ViewScope(viewScopeName, viewScopeNumber);
        updateExternalList();
    }

    /**
     * Sorts the given List with the given criteria and returns the sorted List.
     *
     * @param currentList The List going to be sorted.
     * @return The sorted List.
     */
    @Override
    public List<Income> sort(List<Income> currentList) {
        currentList.sort(sortCriteria.comparator);
        return currentList;
    }

    /**
     * To be implemented when tags are specified.
     *
     * @param currentList The List going to be filtered.
     * @return The filtered List.
     */
    @Override
    public List<Income> filter(List<Income> currentList) {
        return currentList;
    }

    /**
     * Tailors the given List so that only {@code Income} within the given time scope are preserved.
     * The time scope is composed of time unit(e.g. week) and how many (e.g. weeks) ago.
     * Returns the tailored List.
     *
     * @param currentList The list going to be modified.
     * @return The tailored List.
     */
    @Override
    public List<Income> view(List<Income> currentList) {
        return viewScope.view(currentList);
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
     * Returns the total amount of money spent.
     *
     * @return BigDecimal of the total amount of money spent.
     */
    public BigDecimal getTotalAmount() {
        return internalList.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * returns the total Amount given a specific tag.
     *
     * @param tag the tag of
     * @return A BigDecimal which is the sum of all items of a single tag
     */
    public BigDecimal getTagAmount(String tag) {
        return internalList.stream()
                .filter(income -> income.getTags().contains(tag))
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
}