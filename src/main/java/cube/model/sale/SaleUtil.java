package cube.model.sale;

import java.util.Comparator;
import java.util.Date;

class SaleComparator implements Comparator<Sale> {
	public int compare(Sale a, Sale b) {
		return a.getDate().compareTo(b.getDate());
	}
}