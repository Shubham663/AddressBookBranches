package bridgelabz.AddressBookBranches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.AddressBookBranches.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;;

/**
 * Unit test for AddressBook.
 */
public class AddressBookTest {
	AddressBookMain addressBook;
	List<ContactDetails> listContactDetails = null;
	Connection connection = null;
	Logger logger = null;
	PayrollDatabaseService payDataService = null;
	@Before
	public void init() throws JDBCException {
		logger = LogManager.getLogger();
		payDataService = PayrollDatabaseService.getInstance();
		payDataService.loadDriver();
		connection = payDataService.connectToDatabase(connection);
		addressBook = AddressBookMain.getAddressBook();
		addressBook.addContactDetails(new ContactDetails("Shubham","Mittal", "302/Rohtak/Haryana", "Rohtak", "Haryana", 110038, "9876543450", "sh@gmail.com"));
		listContactDetails = new ArrayList<>();
	}

	@Test
	public void writeToFileTest() {
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(addressBook.writeToFile("F:/demo/demo.txt"));
	}

	@Test
	public void readFromFileTest() {
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(addressBook.writeToFile("F:/demo/demo.txt"));
		assertNotNull(addressBook.readFromFile("F:/demo/demo.txt"));
	}
	
	@Test
	public void writeToCSVTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(OpenCSVWriter.writeToCSV());
	}

	@Test
	public void readFromCSVTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		writeToCSVTest();
		assertTrue(OpenCSVWriter.readFromCSV());
	}
	
	@Test
	public void readFromCSVUsingPOJOTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		writeToCSVTest();
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(OpenCSVWriter.readFromCSVUsingPOJO());
	}
	
	@Test
	public void writeToJSONTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(OpenCSVWriter.writeToJSON());
	}

	@Test
	public void readFromJSONTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		OpenCSVWriter.writeToJSON();
		assertTrue(OpenCSVWriter.readFromJSON());
	}
	
	@Test
    public void getDataFromDatabase() throws JDBCException
    {
		listContactDetails = payDataService.getListFromDatabase(connection);
		System.out.println(listContactDetails);
        assertEquals(6, listContactDetails.size(),0);
    }
	
	@Test
    public void getUpdateDataInDatabaseAndAddressbook() throws JDBCException
    {
		listContactDetails = payDataService.getListFromDatabase(connection);
		listContactDetails.get(0).setAddress("Rewadi");
		payDataService.updateDetailsPrepared(connection,"address","Rewadi",listContactDetails.get(0).getFirstName());
		List<ContactDetails> list = payDataService.getListFromDatabase(connection);
		String address = null;
		for(ContactDetails contactDetails : list) {
			if(contactDetails.getFirstName().equals(listContactDetails.get(0).getFirstName())) {
				address = contactDetails.getAddress();
			}
		}
        assertEquals("Rewadi", address);
    }
	
	@Test
    public void getDataInParticularRange() throws JDBCException
    {
		Date date1 = Date.valueOf("2020-05-01");
		Date date2 = Date.valueOf("2020-07-01");
		listContactDetails = payDataService.getDateRange(connection, date1, date2);
        assertEquals(3, listContactDetails.size());
    }
	
	@Test
    public void getContactsInCity() throws JDBCException
    {
		listContactDetails = payDataService.getCityContacts(connection,"Gurugram");
        assertEquals(3, listContactDetails.size());
    }
	
	@Test
    public void getContactsInState() throws JDBCException
    {
		listContactDetails = payDataService.getStateContacts(connection,"Haryana");
        assertEquals(5, listContactDetails.size());
    }
}
