package moomoo.stubs;

import moomoo.task.Category;
import moomoo.task.Expenditure;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class CategoryStub extends Category {
    private String name;
    private ArrayList<ExpenditureStub> category;

    public CategoryStub() {

    }

    public CategoryStub(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public double getCategoryTotalPerMonthYear(int month, int year) {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            Expenditure currExpenditure = category.get(i);
            if (currExpenditure.getDateTime().getMonthValue() == month
                    && currExpenditure.getDateTime().getYear() == year) {
                totalCost += currExpenditure.amount();
            }
        }
        return totalCost;
    }
    @Override
    public void add(Expenditure newExpenditure) {
        category.add(new ExpenditureStub(50, LocalDateTime.of(2019, Month.JULY, 25, 19, 30, 50)));
        category.add(new ExpenditureStub(65, LocalDateTime.of(2019, Month.AUGUST, 21, 19, 30, 50)));
        category.add(new ExpenditureStub(100, LocalDateTime.of(2019, Month.SEPTEMBER, 20, 19, 30, 50)));
        category.add(new ExpenditureStub(40, LocalDateTime.of(2019, Month.JANUARY, 4, 19, 30, 50)));
        category.add(new ExpenditureStub(20, LocalDateTime.of(2020, Month.FEBRUARY, 5, 19, 30, 50)));
    }

}
