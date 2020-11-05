/**
 * 
 */
package bridgelabz.AddressBookBranches;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.bridgelabz.AddressBookBranches.AddressBookJsonService;

import io.restassured.response.Response;

/**
 * @author Shubham
 *
 */
public class AddressbookJsonTest {

	@Test
	public void readListTest() {
		AddressBookJsonService addressBookJsonService = AddressBookJsonService.getInstance();
		Response response = addressBookJsonService.getList();
		String responseAsString = response.asString();
		System.out.println(responseAsString);
		response.then().body("first_name", Matchers.hasItems("Lisa"));
		response.then().body("city", Matchers.hasItems("Jhajjar"));
		response.then().body("zip",Matchers.hasItem(12));
	}
	 
}
