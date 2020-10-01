package bridgelabz.AddressBookBranches;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class AddressBookMain
{
	/**
	 * contactBook , the list of ContactDetails inside AddressBook
	 */
	/**
	 * nameToContact, the map from name to ContactDetails of a person
	 */
	/**
	 * name, the name of the AddressBook
	 */
	/**
	 * nameToAddressBook, the dictionary for storing all the AddressBooks
	 */
	private String name;
	private List<ContactDetails> contactBook = new ArrayList<ContactDetails>();
	private Map<String,ContactDetails> nameToContact = new HashMap<String,ContactDetails>();
	private static Dictionary<String, AddressBookMain> nameToAddressBook = new Hashtable<String, AddressBookMain>(); 
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return ContactDetails, returns object of type ContactDetails after taking i/p from user
	 */
	private static ContactDetails getContactDetails() {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the contact details");
		System.out.println("Enter the first name");
		String firstName= sc.next();
		System.out.println("Enter the last name");
		String lastName = sc.next();
		sc.nextLine(); 					//catches the next line character
		System.out.println("Enter the address");
		String address = sc.nextLine();
		System.out.println("Enter the city");
		String city = sc.nextLine();
		System.out.println("Enter the state");
		String state = sc.nextLine();
		System.out.println("Enter the zip code");
		int zip = sc.nextInt();
		sc.nextLine();  				//catches the next line character
		System.out.println("Enter the phone number");
		String phoneNumber = sc.next();
		System.out.println("Enter the email");
		String email = sc.next();
		ContactDetails cd1 = new ContactDetails(firstName, lastName, address, city, state, zip, phoneNumber, email);
		return cd1;
	}
	
	private boolean duplicateFound(ContactDetails contactDetails) {
		String fName = contactDetails.getFirstName(), lName = contactDetails.getLastName();
		List<Object> duplicate = contactBook.stream().filter(cd -> (cd.getFirstName().equals(fName) && cd.getLastName().equals(lName))).collect(Collectors.toList());
		if(duplicate.size()!=0)
		{
			System.out.println("Contact with this name already exists, not added");
			return true;
		}
//		for(ContactDetails person : contactBook)
//			if(person.equals(contactDetails))
//			{
//				System.out.println("Contact with this name already exists, not added");
//				return true;
//			}
		return false;
	}
	
	/**
	 * addContactDetails(), adds a person's ContactDetails to the AddressBook 
	 */
	private void addContactDetails(ContactDetails contactDetails) {
		if(this.duplicateFound(contactDetails))
			return;
		contactBook.add(contactDetails);
		nameToContact.put(name, contactDetails);
	}
	
	/**
	 * printAddressBook(), prints the ContactDetails present in the AddressBook
	 */
	private void printAddressBook() {
		if(contactBook.size() == 0) {
			System.out.println("No contacts present in the address book: " + this.getName());
			return;
		}
		System.out.println("The contact details for addressbook "+this.getName()+" are as follows:");
		for(int i=0;i <contactBook.size();i++) {
//			System.out.println("Name of Address book: " + this.name);
			System.out.println("\nContact No:"+ (i+1));
			System.out.println(contactBook.get(i));
		}
	}
	
	
	private static void printAllAddressBooks() {
		Enumeration<String> keys = nameToAddressBook.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			nameToAddressBook.get(key).printAddressBook();
		}
	}
	
	private static void getContactsForAddressBooks() {
		Scanner sc= new Scanner(System.in);
		Enumeration<String> keys = nameToAddressBook.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			while(true) {
				System.out.println("Select "
						+ "\n1. Adding contact details"
						+ "\n2. Exit, done adding contacts"
						+ "\n to the addressbook : " + key);
				int addContact = sc.nextInt();
				sc.nextLine();
				if( addContact == 2 )
					break;
			nameToAddressBook.get(key).addContactDetails(getContactDetails());
			}
		}
	}
	
	/**
	 * deleteThrName(), deleting a person's ContactDetails by specifying name
	 */
	private void deleteThrName() {
		Scanner sc= new Scanner(System.in);
		while (true) {
			System.out.println(
					"Would you like to delete any contact from addressbook" + "\n1. Y/y for yes" + "\n2. N/n for no");
			String input = sc.nextLine();
			char del = input.charAt(0);
			if (del == 'Y' || del == 'y') {
				System.out.println("Enter the full name of the contact you want to delete");
				String name = sc.nextLine();
				if (nameToContact.containsKey(name)) {
					contactBook.remove(nameToContact.get(name));
					nameToContact.remove(name);
				}
				else {
					System.out.println("No contact with this name exists");
					continue;
				}
				System.out.println("Deleted successfully");
			}
			else
				break;
		}
	}
	
	/**
	 * editContactDetails(), Provides an option for editing a person's details 
	 * by specifying the name
	 */
	private void editContactDetails() {
		Scanner sc= new Scanner(System.in);
//		char c='N';
		while(true) {
			System.out.print("Would you like to make changes to address book"
					+ "\n1. Y/y for yes"
					+ "\n2. N/n for no"
					+ "\n");
			String input= sc.next();
			/**
			 * @param edit, checks whether user wants to edit details or not
			 */
			char edit = input.charAt(0);
			if(Character.toUpperCase(edit)=='Y') {
				System.out.println("Enter the name of the person whose details you want to edit");
				sc.nextLine();			//catches the new line character
				/**
				 * @param name, checks whose details the user wants to change
				 */
				String name= sc.nextLine();
				if(nameToContact.containsKey(name)){
					ContactDetails cdTemp= nameToContact.get(name);
					System.out.print("Select from options what you want to change in the contact info"
							+ "\n1. First Name"
							+ "\n2. Last Name"
							+ "\n3. Address"
							+ "\n4. City"
							+ "\n5. State"
							+ "\n6. Zip code"
							+ "\n7. Phone Number"
							+ "\n8. Email");
					/**
					 * @param arg, determines what the user wants to change
					 */
					int arg= sc.nextInt();
					sc.nextLine(); //catches the new line character
					switch(arg) {
					case 1: System.out.println("Enter the new first name");
							String firstName = sc.next();
							cdTemp.setFirstName(firstName);
						break;
					case 2: System.out.println("Enter the new last name");
							String lastName = sc.next();
							cdTemp.setLastName(lastName);
						break;
					case 3: System.out.println("Enter the new address");
							String address = sc.nextLine();
							cdTemp.setAddress(address);
						break;
					case 4: System.out.println("Enter the new City");
							String city = sc.nextLine();
							cdTemp.setCity(city);
						break;
					case 5: System.out.println("Enter the new state");
							String state = sc.nextLine();
							cdTemp.setState(state);
						break;
					case 6: System.out.println("Enter the new zip code");
							int zip = sc.nextInt();
							cdTemp.setZip(zip);
						break;
					case 7: System.out.println("Enter the new phone no");
							String phoneNumber = sc.next();
							cdTemp.setPhoneNumber(phoneNumber);
						break;
					case 8: System.out.println("Enter the new mail id");
							String email = sc.next();
							cdTemp.setEmail(email);
						break;
					default: System.out.println("Invalid choice");
							continue;
					}
					System.out.println("The details were updated successfully");
				}
				else
					System.out.println("No contact with this name exists");
			}
			else
				break;
		}
		
	}
	
	
	/**
	 * @return AddressBookMainUC5, returns an Addressbook with only a name
	 */
	private static AddressBookMain getAddressBook() {
		AddressBookMain addressBook = new AddressBookMain();
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the name for the addressbook");
		String name = sc.nextLine();
		addressBook.setName(name);
		nameToAddressBook.put(name, addressBook);
		return addressBook;
	}
	
	
	private static void searchCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. for searching person in city\2. for exiting");
		int proceed = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the first name of person");
		String fName = sc.nextLine();
		System.out.println("Enter the last name of person");
		String lName = sc.nextLine();
		System.out.println("Enter the city in which you want to search");
		String city = sc.nextLine();
		Enumeration<String> keys = nameToAddressBook.keys();
		List<ContactDetails> personInCity = new ArrayList<ContactDetails>();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			AddressBookMain addressBook = nameToAddressBook.get(key);
			personInCity.addAll( addressBook.contactBook.stream().filter(cd -> (cd.getCity().equals(city) && cd.getFirstName().equals(fName) && cd.getLastName().equals(lName))).collect(Collectors.toList()) );
		}
		if(personInCity.size()==0)
			System.out.println("No such person exists in the mentioned city");
		else
			System.out.println(personInCity);
	}
	private static void searchState() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. for searching person in city\n2. for exiting");
		int proceed = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the first name of person");
		String fName = sc.nextLine();
		System.out.println("Enter the last name of person");
		String lName = sc.nextLine();
		System.out.println("Enter the state in which you want to search");
		String state = sc.nextLine();
		Enumeration<String> keys = nameToAddressBook.keys();
		List<ContactDetails> personInState = new ArrayList<ContactDetails>();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			AddressBookMain addressBook = nameToAddressBook.get(key);
			personInState.addAll( addressBook.contactBook.stream().filter(cd -> (cd.getState().equals(state) && cd.getFirstName().equals(fName) && cd.getLastName().equals(lName))).collect(Collectors.toList()) );
		}
		if(personInState.size()==0)
			System.out.println("No such person exists in the mentioned state");
		else
			System.out.println(personInState);
	}
	
	
    public static void main( String[] args ){
    	Scanner sc = new Scanner(System.in);
        System.out.println( "Welcome to Address Book Program" );
        while(true){
        	System.out.println("Select \n1. Adding another addressbook \n2. Exit, if all addressbooks added");
        	int another = sc.nextInt();
        	sc.nextLine();
        	if( another == 2 )
        		break;
        	AddressBookMain addressBook = getAddressBook();
        }
        getContactsForAddressBooks();
        searchCity();
        searchState();
//        addressBook.editContactDetails();
//        addressBook.deleteThrName();
        printAllAddressBooks();
	}
		
}

