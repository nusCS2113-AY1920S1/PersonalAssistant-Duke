package cube.model.sale;

import java.util.Comparator;
import java.util.Date;

class SalesComparator implements Comparator<Sale> {
	@Override
	public int compare(Sale a, Sale b) {
		return a.getDate().compareTo(b.getDate());
	}
}