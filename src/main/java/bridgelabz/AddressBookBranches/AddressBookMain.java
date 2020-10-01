package bridgelabz.AddressBookBranches;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
	private List<ContactDetails> contactBook = new ArrayList<ContactDetails>();
	
	private Map<String,ContactDetails> nameToContact = new HashMap<String,ContactDetails>();
	
	
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
	
	
	/**
	 * addContactDetails(), adds a person's ContactDetails to the AddressBook 
	 */
	private void addContactDetails(ContactDetails contactDetails) {
		contactBook.add(contactDetails);
		String name= contactDetails.getFirstName() + " " + contactDetails.getLastName();
		nameToContact.put(name, contactDetails);
	}
	
	/**
	 * printAddressBook(), prints the ContactDetails present in the AddressBook
	 */
	private void printAddressBook() {
		System.out.println("The contact details are as follows:");
		for(int i=0;i <contactBook.size();i++) {
			System.out.println("\n"+"Contact No:"+ (i+1));
			System.out.println(contactBook.get(i));
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
				sc.nextLine();       //catches the next line character
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
     * @param addressBook, an AddressBook for storing ContactDetails
     */
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Address Book Program" );
        AddressBookMain addressBook = new AddressBookMain();
        addressBook.addContactDetails(getContactDetails());
        addressBook.editContactDetails();
        addressBook.printAddressBook();   
    }
}