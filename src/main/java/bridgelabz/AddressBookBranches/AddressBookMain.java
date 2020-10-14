package bridgelabz.AddressBookBranches;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
public class AddressBookMain {
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
	private Map<String, ContactDetails> nameToContact = new HashMap<String, ContactDetails>();
	private static Dictionary<String, AddressBookMain> nameToAddressBook = new Hashtable<String, AddressBookMain>();
	private static Map<String, List<ContactDetails>> cityToContacts = new HashMap<String, List<ContactDetails>>();
	private static Map<String, List<ContactDetails>> stateToContacts = new HashMap<String, List<ContactDetails>>();

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
	 * @return ContactDetails, returns object of type ContactDetails after taking
	 *         i/p from user
	 */
	public static ContactDetails getContactDetails() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the contact details");
		System.out.println("Enter the first name");
		String firstName = sc.next();
		System.out.println("Enter the last name");
		String lastName = sc.next();
		sc.nextLine(); // catches the next line character
		System.out.println("Enter the address");
		String address = sc.nextLine();
		System.out.println("Enter the city");
		String city = sc.nextLine();
		System.out.println("Enter the state");
		String state = sc.nextLine();
		System.out.println("Enter the zip code");
		int zip = sc.nextInt();
		sc.nextLine(); // catches the next line character
		System.out.println("Enter the phone number");
		String phoneNumber = sc.next();
		System.out.println("Enter the email");
		String email = sc.next();
		ContactDetails cd1 = new ContactDetails(firstName, lastName, address, city, state, zip, phoneNumber, email);
		return cd1;
	}

	private boolean duplicateFound(ContactDetails contactDetails) {
		String fName = contactDetails.getFirstName(), lName = contactDetails.getLastName();
		List<Object> duplicate = contactBook.stream()
				.filter(cd -> (cd.getFirstName().equals(fName) && cd.getLastName().equals(lName)))
				.collect(Collectors.toList());
		if (duplicate.size() != 0) {
			System.out.println("Contact with this name already exists, not added");
			return true;
		}
		return false;
	}

	/**
	 * addContactDetails(), adds a person's ContactDetails to the AddressBook
	 */
	public void addContactDetails(ContactDetails contactDetails) {
		if (this.duplicateFound(contactDetails))
			return;
		if (cityToContacts.containsKey(contactDetails.getCity()))
			cityToContacts.get(contactDetails.getCity()).add(contactDetails);
		else {
			List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
			contactDetailsList.add(contactDetails);
			cityToContacts.put(contactDetails.getCity(), contactDetailsList);
		}
		if (stateToContacts.containsKey(contactDetails.getState()))
			stateToContacts.get(contactDetails.getState()).add(contactDetails);
		else {
			List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
			contactDetailsList.add(contactDetails);
			stateToContacts.put(contactDetails.getState(), contactDetailsList);
		}
		contactBook.add(contactDetails);
		nameToContact.put(name, contactDetails);
	}

	/**
	 * printAddressBook(), prints the ContactDetails present in the AddressBook
	 */
	private void printAddressBook() {
		if (contactBook.size() == 0) {
			System.out.println("No contacts present in the address book: " + this.getName());
			return;
		}
		System.out.println("The contact details for addressbook " + this.getName() + " are as follows:");
		for (int i = 0; i < contactBook.size(); i++) {
//			System.out.println("Name of Address book: " + this.name);
			System.out.println("\nContact No:" + (i + 1));
			System.out.println(contactBook.get(i));
		}
	}

	private static void printAllAddressBooks() {
		Enumeration<String> keys = nameToAddressBook.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			nameToAddressBook.get(key).printAddressBook();
		}
	}

	private static void getContactsForAddressBooks() {
		Scanner sc = new Scanner(System.in);
		Enumeration<String> keys = nameToAddressBook.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			while (true) {
				System.out.println("Select " + "\n1. Adding contact details" + "\n2. Exit, done adding contacts"
						+ "\n to the addressbook : " + key);
				int addContact = sc.nextInt();
				sc.nextLine();
				if (addContact == 2)
					break;
				nameToAddressBook.get(key).addContactDetails(getContactDetails());
			}
		}
	}

	/**
	 * deleteThrName(), deleting a person's ContactDetails by specifying name
	 */
	private void deleteThrName() {
		Scanner sc = new Scanner(System.in);
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
				} else {
					System.out.println("No contact with this name exists");
					continue;
				}
				System.out.println("Deleted successfully");
			} else
				break;
		}
	}

	/**
	 * editContactDetails(), Provides an option for editing a person's details by
	 * specifying the name
	 */
	private void editContactDetails() {
		Scanner sc = new Scanner(System.in);
//		char c='N';
		while (true) {
			System.out.print(
					"Would you like to make changes to address book" + "\n1. Y/y for yes" + "\n2. N/n for no" + "\n");
			String input = sc.next();
			/**
			 * @param edit, checks whether user wants to edit details or not
			 */
			char edit = input.charAt(0);
			if (Character.toUpperCase(edit) == 'Y') {
				System.out.println("Enter the name of the person whose details you want to edit");
				sc.nextLine(); // catches the new line character
				/**
				 * @param name, checks whose details the user wants to change
				 */
				String name = sc.nextLine();
				if (nameToContact.containsKey(name)) {
					ContactDetails cdTemp = nameToContact.get(name);
					System.out.print("Select from options what you want to change in the contact info"
							+ "\n1. First Name" + "\n2. Last Name" + "\n3. Address" + "\n4. City" + "\n5. State"
							+ "\n6. Zip code" + "\n7. Phone Number" + "\n8. Email");
					/**
					 * @param arg, determines what the user wants to change
					 */
					int arg = sc.nextInt();
					sc.nextLine(); // catches the new line character
					switch (arg) {
					case 1:
						System.out.println("Enter the new first name");
						String firstName = sc.next();
						cdTemp.setFirstName(firstName);
						break;
					case 2:
						System.out.println("Enter the new last name");
						String lastName = sc.next();
						cdTemp.setLastName(lastName);
						break;
					case 3:
						System.out.println("Enter the new address");
						String address = sc.nextLine();
						cdTemp.setAddress(address);
						break;
					case 4:
						System.out.println("Enter the new City");
						String city = sc.nextLine();
						cdTemp.setCity(city);
						break;
					case 5:
						System.out.println("Enter the new state");
						String state = sc.nextLine();
						cdTemp.setState(state);
						break;
					case 6:
						System.out.println("Enter the new zip code");
						int zip = sc.nextInt();
						cdTemp.setZip(zip);
						break;
					case 7:
						System.out.println("Enter the new phone no");
						String phoneNumber = sc.next();
						cdTemp.setPhoneNumber(phoneNumber);
						break;
					case 8:
						System.out.println("Enter the new mail id");
						String email = sc.next();
						cdTemp.setEmail(email);
						break;
					default:
						System.out.println("Invalid choice");
						continue;
					}
					System.out.println("The details were updated successfully");
				} else
					System.out.println("No contact with this name exists");
			} else
				break;
		}

	}

	/**
	 * @return AddressBookMainUC5, returns an AddressBook with only a name
	 */
	public static AddressBookMain getAddressBook() {
		AddressBookMain addressBook = new AddressBookMain();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name for the addressbook");
		String name = sc.nextLine();
		addressBook.setName(name);
		nameToAddressBook.put(name, addressBook);
		return addressBook;
	}

	private static void searchCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. for searching person in city" + "\n2. for exiting");
		int proceed = sc.nextInt();
		sc.nextLine();
		if (proceed == 2)
			return;
		System.out.println("Enter the first name of person");
		String fName = sc.nextLine();
		System.out.println("Enter the last name of person");
		String lName = sc.nextLine();
		System.out.println("Enter the city in which you want to search");
		String city = sc.nextLine();
		Enumeration<String> keys = nameToAddressBook.keys();
		List<ContactDetails> personInCity = new ArrayList<ContactDetails>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			AddressBookMain addressBook = nameToAddressBook.get(key);
			personInCity.addAll(addressBook.contactBook.stream().filter(cd -> (cd.getCity().equals(city)
					&& cd.getFirstName().equals(fName) && cd.getLastName().equals(lName)))
					.collect(Collectors.toList()));
		}
		if (personInCity.size() == 0)
			System.out.println("No such person exists in the mentioned city");
		else
			System.out.println(personInCity);
	}

	private static void searchState() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. for searching person in state" + "\n2. for exiting");
		int proceed = sc.nextInt();
		if (proceed == 2)
			return;
		sc.nextLine();
		System.out.println("Enter the first name of person");
		String fName = sc.nextLine();
		System.out.println("Enter the last name of person");
		String lName = sc.nextLine();
		System.out.println("Enter the state in which you want to search");
		String state = sc.nextLine();
		Enumeration<String> keys = nameToAddressBook.keys();
		List<ContactDetails> personInState = new ArrayList<ContactDetails>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			AddressBookMain addressBook = nameToAddressBook.get(key);
			personInState.addAll(addressBook.contactBook.stream().filter(cd -> (cd.getState().equals(state)
					&& cd.getFirstName().equals(fName) && cd.getLastName().equals(lName)))
					.collect(Collectors.toList()));
		}
		if (personInState.size() == 0)
			System.out.println("No such person exists in the mentioned state");
		else
			System.out.println(personInState);
	}

	private static void printPersonsInCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. For getting persons of a city" + "\n2. for skipping");
		int progress = sc.nextInt();
		sc.nextLine();
		if (progress == 2)
			return;
		System.out.println("Enter the city whose persons contacts you want to get");
		String city = sc.nextLine();
		if (cityToContacts.containsKey(city))
			System.out.println(cityToContacts.get(city));
		else
			System.out.println("No person present in city");
	}

	private static void printPersonsInState() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select \n1. For getting persons of a State" + "\n2. for skipping");
		int progress = sc.nextInt();
		sc.nextLine();
		if (progress == 2)
			return;
		System.out.println("Enter the State whose persons contacts you want to get");
		String state = sc.nextLine();
		if (stateToContacts.containsKey(state))
			System.out.println(stateToContacts.get(state));
		else
			System.out.println("No persons present in State");
	}

	private static void personCountInCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select" + "\n1. for getting count of persons in a city" + "\n2. for exiting");
		int proceed = sc.nextInt();
		sc.nextLine();
		if (proceed == 2)
			return;
		System.out.println("Enter the city of whose person count you want");
		String city = sc.nextLine();
		if (cityToContacts.containsKey(city))
			System.out.println("The number of persons in city " + city + " are " + cityToContacts.get(city).size());
	}

	private static void personCountInState() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select" + "\n1. for getting count of persons in a state" + "\n2. for exiting");
		int proceed = sc.nextInt();
		sc.nextLine();
		if (proceed == 2)
			return;
		System.out.println("Enter the state of whose person count you want");
		String state = sc.nextLine();
		if (stateToContacts.containsKey(state))
			System.out.println("The number of persons in state " + state + " are " + stateToContacts.get(state).size());
	}

	private static void sortByPersonName() {
		Scanner sc = new Scanner(System.in);
		CustomComparator c = new CustomComparator();
		System.out.println("Select" + "\n1. For sorting entries by name in an addressbook"
				+ "\n2. For sorting entries by name in all addressbooks" + "\n3. For exiting");
		int proceed = sc.nextInt();
		sc.nextLine(); // catches the newline character
		switch (proceed) {
		case 1:
			System.out.println("Enter the name of the addressbook whose details you want to sort");
			String input = sc.nextLine();
			AddressBookMain addBook = nameToAddressBook.get(input);
			addBook.contactBook.stream().sorted(c).forEach(System.out::println);
			break;
		case 2:
			Enumeration<String> keys = nameToAddressBook.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				AddressBookMain addBook2 = nameToAddressBook.get(key);
				System.out.println("The sorted coontact details of addressbook " + key + " are:");
				addBook2.contactBook.stream().sorted(c).forEach(System.out::println);
			}
			break;
		default:
			System.out.println("Exiting without sorting");
			break;
		}
	}

	private static void sortByStateCityPin() {
		Scanner sc = new Scanner(System.in);
		CustomComparatorState c1 = new CustomComparatorState();
		CustomComparatorCity c2 = new CustomComparatorCity();
		CustomComparatorZip c3 = new CustomComparatorZip();
		System.out.println("Select" + "\n1. For sorting entries in an addressbook by state"
				+ "\n2. For sorting entries in an addressbook by city"
				+ "\n3. For sorting entries in an addressbook by pin" + "\n4. For exiting");
		int proceed = sc.nextInt();
		sc.nextLine(); // catches the newline character
		switch (proceed) {
		case 1:
			System.out.println("Enter the name of the addressbook whose details you want to sort by state");
			String input = sc.nextLine();
			AddressBookMain addBook = nameToAddressBook.get(input);
			addBook.contactBook.stream().sorted(c1).forEach(System.out::println);
			break;
		case 2:
			System.out.println("Enter the name of the addressbook whose details you want to sort by city");
			String input2 = sc.nextLine();
			AddressBookMain addBook2 = nameToAddressBook.get(input2);
			addBook2.contactBook.stream().sorted(c2).forEach(System.out::println);
			break;
		case 3:
			System.out.println("Enter the name of the addressbook whose details you want to sort by zip");
			String input3 = sc.nextLine();
			AddressBookMain addBook3 = nameToAddressBook.get(input3);
			addBook3.contactBook.stream().sorted(c3).forEach(System.out::println);
			break;
		default:
			System.out.println("Exiting without sorting");
			break;
		}
	}

	public static boolean writeToFile(String filePath) {
		Enumeration<String> keys = nameToAddressBook.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			AddressBookMain addressBook = nameToAddressBook.get(key);
			for (int i = 0; i < addressBook.contactBook.size(); i++) {
				Path path = Paths.get(filePath);
				if (!Files.exists(path))
					return false;
				try {
					Files.write(path, (addressBook.contactBook.get(i).toString()).getBytes(),
							StandardOpenOption.APPEND);
					return true;
				} catch (IOException e) {
					System.out.println("The file was not found");
				}
			}
		}
		return false;
	}

	public static List<ContactDetails> readFromFile(String filePath) {
		Path path = Paths.get(filePath);
		List<ContactDetails> addressBook = new ArrayList<>();
		try {
			String fileContent = new String(Files.readAllBytes(path));
			String[] contacts = fileContent.split("\n");
			for (String contact : contacts) {
				String[] details = contact.split(",");
				String firstName = details[0].split(":")[1];
				String lastName = details[1].split(":")[1];
				String address = details[2].split(":")[1];
				String city = details[3].split(":")[1];
				String state = details[4].split(":")[1];
				int zip = Integer.parseInt(details[5].split(":")[1]);
				String phoneNumber = details[6].split(":")[1];
				String email = details[7].split(":")[1];
				addressBook.add(new ContactDetails(firstName, lastName, address, city, state, zip, phoneNumber, email));
			}
			System.out.println(addressBook);
			return addressBook;
		} catch (IOException e) {
			System.out.println("The mentioned directory was not found");
		}
		return null;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Address Book Program");
		while (true) {
			System.out.println("Select \n1. Adding another addressbook \n2. Exit, if all addressbooks added");
			int another = sc.nextInt();
			sc.nextLine();
			if (another == 2)
				break;
			AddressBookMain addressBook = getAddressBook();
		}
		getContactsForAddressBooks();
		searchCity();
		searchState();
		printPersonsInCity();
		printPersonsInState();
		personCountInCity();
		personCountInState();
		printAllAddressBooks();
		sortByPersonName();
		sortByStateCityPin();
		System.out.println("Thank You for using the AddressBook Program");
		writeToFile("F:/demo/demo.txt");
		readFromFile("F:/demo/demo.txt");
	}
}
