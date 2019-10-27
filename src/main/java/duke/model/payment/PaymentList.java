package duke.model.payment;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaymentList {

    private static final SortCriteria DEFAULT_SORT_CRITERIA = SortCriteria.TIME;

    private List<Payment> internalTimeSortedList;

    private List<Payment> internalAmountSortedList;

    private List<Payment> internalPrioritySortedList;

    private ObservableList<Payment> externalList;

    private ObservableList<Payment> externalFinalList;

    private SortCriteria sortCriteria;

    private enum SortCriteria {
        TIME, AMOUNT, PRIORITY;
    }

    public PaymentList() {
        this.internalTimeSortedList = new ArrayList<>();

        externalList = FXCollections.observableArrayList();
        externalFinalList = FXCollections.unmodifiableObservableList(externalList);

        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();

        sortCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToExternalList();
    }

    public PaymentList(List<Payment> timeSortedList) {
        this.internalTimeSortedList = timeSortedList;

        externalList = FXCollections.observableArrayList();
        externalFinalList = FXCollections.unmodifiableObservableList(externalList);

        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();

        sortCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToExternalList();
    }

    public void add(Payment payment) {
        internalTimeSortedList.add(payment);
        internalTimeSortedList.sort(Comparator.comparing(Payment::getDue));

        updateInternalAmountSortedList(); // add operation may break the sorted order.
        updateInternalPrioritySortedList();

        fetchInternalListToExternalList();
    }

    public void remove(Payment payment) {
        internalTimeSortedList.remove(payment);
        internalAmountSortedList.remove(payment);
        internalPrioritySortedList.remove(payment); // remove operation doesn't break the sorted order.

        fetchInternalListToExternalList();
    }

    public void setPayment(Payment target, Payment editedPayment) {
        remove(target);
        add(editedPayment); // using add method can help sort after change of element.
    }

    public void setSortCriteria(String sortCriteria) throws DukeException {
        try {
            this.sortCriteria = SortCriteria.valueOf(sortCriteria.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SORT_CRITERIA_INVALID, sortCriteria));
        }
        fetchInternalListToExternalList();
    }

    public ObservableList<Payment> getExternalFinalList() {
        return externalFinalList;
    }

    /**
     * Returns all internal payments as a list.
     * This is for storage ONLY!
     *
     * @return list containing all internal payments.
     */
    public List<Payment> getInternalList() {
        return internalTimeSortedList;
    }

    private void fetchInternalListToExternalList() {
        switch (sortCriteria) {
            case TIME:
                externalList.setAll(internalTimeSortedList);
                break;

            case AMOUNT:
                externalList.setAll(internalAmountSortedList);
                break;

            case PRIORITY:
                externalList.setAll(internalPrioritySortedList);
                break;
        }
    }

    private void updateInternalAmountSortedList() {
        internalAmountSortedList = new ArrayList<>(internalTimeSortedList);
        internalAmountSortedList.sort(Comparator.comparing(Payment::getAmount));
        Collections.reverse(internalAmountSortedList);
    }

    private void updateInternalPrioritySortedList() {
        internalPrioritySortedList = new ArrayList<>(internalTimeSortedList);
        internalPrioritySortedList.sort(Comparator.comparing(Payment::getNumeratedPriority));
        Collections.reverse(internalPrioritySortedList);
    }
}
