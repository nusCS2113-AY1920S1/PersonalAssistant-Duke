package duke.dukeobject;

import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseList extends DukeList<Expense> {

    private static enum SortCriteria {
        AMOUNT {
            public List<Expense> sort(List<Expense> currentList) {
                currentList.sort(Comparator.comparing(Expense::getAmount));
                return currentList;
            }
        }, TIME {
            public List<Expense> sort(List<Expense> currentList) {
                currentList.sort(Comparator.comparing(Expense::getTime));
                return currentList;
            }
        }, DESCRIPTION {
            public List<Expense> sort(List<Expense> currentList) {
                currentList.sort(Comparator.comparing(Expense::getDescription));
                return currentList;
            }
        };

        public abstract List<Expense> sort(List<Expense> currentList);
    }

    private static enum ViewScope {
        DAY {
            @Override
            public List<Expense> view(List<Expense> currentList) {
                return currentList.stream()
                        .filter(e -> {
                            boolean isSameYear = (e.getTime().getYear() == now.minusDays(previous).getYear());
                            boolean isSameMonth = (e.getTime().getMonth() == now.minusDays(previous).getMonth());
                            boolean isSameDay = (e.getTime().getDayOfMonth() == now.minusDays(previous).getDayOfMonth());
                            return (isSameYear && isSameMonth && isSameDay);
                        })
                        .collect(Collectors.toList());
            }
        }, WEEK {
            @Override
            public List<Expense> view(List<Expense> currentList) {
                return currentList.stream()
                        .filter(e -> {
                            int dayOfWeek =  e.getTime().getDayOfWeek().getValue();
                            // constructs three LocalDate objects as var
                            var end = e.getTime().minusDays(dayOfWeek - 1).toLocalDate();
                            var start = e.getTime().plusDays(7 - dayOfWeek).toLocalDate();
                            var current = now.minusWeeks(previous).toLocalDate();

                            return (current.equals(end) || current.equals(start) ||
                                    (current.isAfter(start) && current.isBefore(end)));
                        })
                        .collect(Collectors.toList());
            }
        }, MONTH {
            public List<Expense> view(List<Expense> currentList) {
                return currentList.stream()
                        .filter(e -> {
                            boolean isSameYear = (e.getTime().getYear() == now.minusDays(previous).getYear());
                            boolean isSameMonth = (e.getTime().getMonth() == now.minusDays(previous).getMonth());
                            return (isSameYear && isSameMonth);
                        })
                        .collect(Collectors.toList());
            }
        }, YEAR {
            public List<Expense> view(List<Expense> currentList) {
                return currentList.stream()
                        .filter(e -> {
                            boolean isSameYear = (e.getTime().getYear() == now.minusDays(previous).getYear());
                            return (isSameYear);
                        })
                        .collect(Collectors.toList());
            }
        }, ALL {
            public List<Expense> view(List<Expense> currentList) {
                return currentList;
            }
        };

        protected int previous;
        public void setPrevious(int previous) {
            this.previous = previous;
        }

        protected LocalDateTime now = LocalDateTime.now();
        public abstract List<Expense> view(List<Expense> currentList);
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
        List<Expense> externalList;
        externalList = filter(sort(view(internalList)));
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
            throw new DukeException(String.format(DukeException.MESSAGE_EXPENSE_SORT_CRITERIA_INVALID, sortCriteria));
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
    public void setViewScope(String viewScope, int previous) throws DukeException {
        try {
            this.viewScope = ViewScope.valueOf(viewScope.toUpperCase());
            this.viewScope.setPrevious(previous);
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(
                    DukeException.MESSAGE_EXPENSE_VIEW_SCOPE_STRING_INVALID, viewScope));
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
        return sortCriteria.sort(currentList);
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
     * The time scope is composed of time unit(e.g. week) and how many (e.g. weeks) ago.
     * Returns the tailored List.
     *
     * @param currentList The list going to be modified.
     * @return The tailored List.
     */
    @Override
    public List<Expense> view(List<Expense> currentList) {
        return viewScope.view(currentList);
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

    public BigDecimal getTotalExternalAmount() {
        return externalList.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}