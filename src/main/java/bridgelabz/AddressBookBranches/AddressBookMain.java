package bridgelabz.AddressBookBranches;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
/**
 * @author Shubham, class AddressBookMainUC2 for keeping addressbooks.
 *
 */
public class AddressBookMain
{
	/**
	 * contactBook , the list of ContactDetails inside AddressBook
	 */
	List<ContactDetails> contactBook = new ArrayList<ContactDetails>();
	
	
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
     * @param addressBook, an AddressBook for storing ContactDetails
     */
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Address Book Program" );
        AddressBookMain addressBook = new AddressBookMain();
        addressBook.contactBook.add(getContactDetails());
        addressBook.printAddressBook();   
    }
}
