package cube.model.sale;

import java.util.TreeSet;

public class SalesHistory {
	private TreeSet<Sale> salesSet;

	public SalesHistory() {
		this.salesSet = new TreeSet<Sale>(new SaleComparator());
	}

	public SalesHistory(TreeSet<Sale> salesSet) {
		this.salesSet = salesSet;
	}

}