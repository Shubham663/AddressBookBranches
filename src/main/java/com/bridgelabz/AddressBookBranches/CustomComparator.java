package com.bridgelabz.AddressBookBranches;

import java.util.Comparator;

public class CustomComparator implements Comparator<ContactDetails> {
	@Override
	public int compare(ContactDetails o1, ContactDetails o2) {
		// TODO Auto-generated method stub
		int k = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
		if (k < 0)
			return -1;
		else if (k > 0)
			return 1;
		else {
			int l = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			if (l < 0)
				return -1;
			return 1;
		}

	}
}

class CustomComparatorCity implements Comparator<ContactDetails> {
	@Override
	public int compare(ContactDetails o1, ContactDetails o2) {
		// TODO Auto-generated method stub
		int k = o1.getCity().compareToIgnoreCase(o2.getCity());
		if (k < 0)
			return -1;
		else if (k > 0)
			return 1;
		else
			return 0;
	}
}

class CustomComparatorState implements Comparator<ContactDetails> {
	@Override
	public int compare(ContactDetails o1, ContactDetails o2) {
		// TODO Auto-generated method stub
		int k = o1.getState().compareToIgnoreCase(o2.getState());
		if (k < 0)
			return -1;
		else if (k > 0)
			return 1;
		else
			return 0;
	}
}

class CustomComparatorZip implements Comparator<ContactDetails> {
	@Override
	public int compare(ContactDetails o1, ContactDetails o2) {
		// TODO Auto-generated method stub
		int k = o1.getZip() - o2.getZip();
		if (k < 0)
			return -1;
		else if (k > 0)
			return 1;
		else
			return 0;
	}
}