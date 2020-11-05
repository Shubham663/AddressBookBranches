package com.bridgelabz.AddressBookBranches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	Logger logger = LogManager.getLogger();
	
	private AddressBookJsonService() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		logger = LogManager.getLogger();
	}
	public static AddressBookJsonService getInstance() {
		if(addressBookJsonService == null)
			addressBookJsonService = new AddressBookJsonService();
		return addressBookJsonService;
	}
	public List<Response> getList() {
		List<Response> responses = new ArrayList<>();
		Map<Integer,Boolean> contactAddStatus = new HashMap<>();
		Runnable task  =() -> {
			contactAddStatus.put(1, false);
			responses.add(RestAssured.get("/AddressBook/list"));
			contactAddStatus.put(1,true);
		};
		Thread thread = new Thread(task,"getList");
		thread.start();
		while(contactAddStatus.containsValue(false) || responses.size() < 1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				logger.error("Error while waiting for threads to finish in getList " + e.getMessage());
			}
		}
		return responses;
	}
	public synchronized Response addContactToServer(ContactDetails contactDetails) {
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
	
	public Boolean addMultipleContactsToJsonServer(List<ContactDetails> list) {
		Response response = null;
		Map<Integer,Boolean> contactAddStatus = new HashMap<>();
		list.forEach(contact -> {
			Runnable task = () -> {
				contactAddStatus.put(contact.hashCode(), false);
				final Response response2 = addContactToServer(contact);
				
				contactAddStatus.put(contact.hashCode(), true);
			};
			Thread thread = new Thread(task,contact.getFirstName());
			thread.start();
		});
		while(contactAddStatus.size()<list.size() || contactAddStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				logger.error("Error while waiting for threads to finish " + e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public Response updateContactsInJsonServer(ContactDetails contactDetails, int id) {
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
				.put("/AddressBook/update/"+id);
		return response;
	}
	public Response deleteContactFromJsonServer(int id) {
		Response response = RestAssured.delete("/AddressBook/delete/"+id);
		return response;
	}
}
