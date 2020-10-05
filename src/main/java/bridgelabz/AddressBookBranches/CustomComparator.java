package bridgelabz.AddressBookBranches;

import java.util.Comparator;

public class CustomComparator implements Comparator<ContactDetails>{
	@Override
	public int compare(ContactDetails o1, ContactDetails o2) {
		// TODO Auto-generated method stub
		int k = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
		if(k < 0 )
			return -1;
		else if(k > 0)
			return 1;
		else {
			int l = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			if(l < 0)
				return -1;
			return 1;
		}
			
	}
}