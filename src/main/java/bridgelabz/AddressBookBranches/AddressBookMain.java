package bridgelabz.AddressBookBranches;

import bridgelabz.AddressBookBranches.ContactDetails;

/**
 * Hello world!
 *
 */
/**
 * @author Shubham . class AddressBookMainUC1 for storing multiple Contact Details
 *
 */
public class AddressBookMain 
{
    /**
     * @param args , provides command line arguments
     */
    /**
     * @param cd1 , stores the contact details
     */
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Address Book Program" );
        ContactDetails cd1= new ContactDetails("Shubham", "Mittal","H.No. 32", "Mumbai", "Maharashtra", 400004, "9033334444", "shivajiCool@gmail.com");
        System.out.println(cd1);
    }
}
