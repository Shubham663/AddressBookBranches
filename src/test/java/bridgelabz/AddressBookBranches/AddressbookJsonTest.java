/**
 * 
 */
package bridgelabz.AddressBookBranches;

import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
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
	
	@Test
	public void addMultipleContactsToJsonServerTest_returnsTrueWhenAdditionSuccessful() {
		ContactDetails contactDetails = new ContactDetails("Aamir","Khan", "302", "Rohtak", "Haryana", 110038, "9876983450", "ak@gmail.com");
		ContactDetails contactDetails2 = new ContactDetails("Salman","Khan", "305", "Rohtak", "Haryana", 110038, "9876983450", "sk@gmail.com");
		List<ContactDetails> list = new ArrayList<>();
		list.add(contactDetails);
		list.add(contactDetails2);
		AddressBookJsonService aService = AddressBookJsonService.getInstance();
		Boolean boolean1 = aService.addMultipleContactsToJsonServer(list);
		assertThat("Contacts not added", true, Matchers.is(true));
		Response response = aService.getList().get(0);
		String responseAsString = response.asString();
		response.then().body("first_name", Matchers.hasItems("Aamir","Salman"));
		response.then().body("city", Matchers.hasItems("Rohtak"));
		response.then().body("zip",Matchers.hasItem(110038));
	}
	
	@Test
	public void updateContactsInJsonServerTest() {
		ContactDetails contactDetails = new ContactDetails("Imran","Khan", "302", "Rohtak", "Haryana", 110038, "9876983450", "ak@gmail.com");
		AddressBookJsonService aService = AddressBookJsonService.getInstance();
		Response response = aService.updateContactsInJsonServer(contactDetails,1);
		String responseAsString = response.asString();
		System.out.println(responseAsString);
		response.then().body("address", Matchers.is("302"));
		response.then().body("first_name", Matchers.is("Imran"));
		response.then().body("state",Matchers.is("Haryana"));
	}
	
	@Test
	public void deleteContactFromJsonServerTest() {
		AddressBookJsonService aService = AddressBookJsonService.getInstance();
		Response response = aService.deleteContactFromJsonServer(2);
		String responseAsString = response.asString();
		int status = response.getStatusCode();
		assertThat(status, CoreMatchers.is(200));
		response = aService.getList().get(0);
		response.then().body("id", Matchers.not(2));
	}
	
}
