package utils;

import executor.accessors.*;

public enum AccessType {
    DENY(AccessDeny.class),
    BALANCE(AccessWalletBalance.class),
    EXPENSES(AccessWalletExpenses.class),
    TASKLIST(AccessTaskList.class),
    WALLET(AccessWallet.class),
    PIE_CHART_DATA(AccessPieChartData.class);

    private final Class accessClass;

    /**
     * Constructor for 'AccessType' enum.
     */
    private AccessType(Class accessClass) {
        this.accessClass = accessClass;
    }

    // Setters & Getters

    public Class getAccessClass() {
        return accessClass;
    }
}
