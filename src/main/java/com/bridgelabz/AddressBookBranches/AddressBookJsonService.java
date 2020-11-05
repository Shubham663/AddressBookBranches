package com.bridgelabz.AddressBookBranches;

import io.restassured.RestAssured;
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
}
