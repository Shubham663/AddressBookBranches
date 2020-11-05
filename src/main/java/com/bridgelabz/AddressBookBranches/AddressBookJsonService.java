package com.bridgelabz.AddressBookBranches;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Service class for interacting with json server
 * @author Shubham
 *
 */
public class AddressBookJsonService {
	private static AddressBookJsonService addressBookJsonService;
	private AddressBookJsonService() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	public static AddressBookJsonService getInstance() {
		if(addressBookJsonService == null)
			addressBookJsonService = new AddressBookJsonService();
		return addressBookJsonService;
	}
	public Response getList() {
		Response response = RestAssured.get("/AddressBook/list");
		return response;
	}
	public Response addContactToServer(ContactDetails contactDetails) {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body("{\"first_name\": \""+contactDetails.getFirstName()+
						"\",\"last_name\": \""+ contactDetails.getLastName() +
						"\",\"address\": \"" + contactDetails.getAddress() +
						"\",\"city\": \"" + contactDetails.getCity() +
						"\",\"state\": \"" + contactDetails.getState() +
						"\",\"zip\": " + contactDetails.getZip() +
						",\"phoneNumber\": \"" + contactDetails.getPhoneNumber() +
						"\",\"email\": \"" + contactDetails.getEmail() + "\"}")
				.when()
				.post("/AddressBook/create");
		return response;
	}
}
