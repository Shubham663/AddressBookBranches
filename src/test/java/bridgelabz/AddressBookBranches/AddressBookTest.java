package bridgelabz.AddressBookBranches;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import bridgelabz.AddressBookBranches.*;;

/**
 * Unit test for AddressBook.
 */
public class AddressBookTest {
	AddressBookMain addressBook;

	@Before
	public void init() {
		addressBook = AddressBookMain.getAddressBook();
		addressBook.addContactDetails(new ContactDetails("Shubham","Mittal", "302", "K", "H", 21, "1245341212", "gmail.com"));
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
		assertTrue(OpenCSVWriter.readFromCSV());
	}
	
	@Test
	public void readFromCSVUsingPOJOTest() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
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
}
