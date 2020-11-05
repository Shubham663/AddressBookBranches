/**
 * 
 */
package bridgelabz.AddressBookBranches;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.bridgelabz.AddressBookBranches.AddressBookJsonService;
import com.bridgelabz.AddressBookBranches.ContactDetails;

import io.restassured.response.Response;

/**
 * @author Shubham
 *
 */
public class AddressbookJsonTest {

	@Test
	public void readListTest() {
		AddressBookJsonService addressBookJsonService = AddressBookJsonService.getInstance();
		Response response = addressBookJsonService.getList().get(0);
		String responseAsString = response.asString();
		response.then().body("first_name", Matchers.hasItems("Lisa"));
		response.then().body("city", Matchers.hasItems("Jhajjar"));
		response.then().body("zip",Matchers.hasItem(12));
	}
	 
	@Test
	public void writeContactTest() {
		ContactDetails contactDetails = new ContactDetails("Aamir","Khan", "302", "Rohtak", "Haryana", 110038, "9876983450", "sk@gmail.com");
		AddressBookJsonService addressBookJsonService = AddressBookJsonService.getInstance();
		Response response = addressBookJsonService.addContactToServer(contactDetails);
		String responseAsString = response.asString();
		response.then().body("first_name", Matchers.is("Aamir"));
		response.then().body("city", Matchers.is("Rohtak"));
		response.then().body("zip",Matchers.is(110038));
	}
}
