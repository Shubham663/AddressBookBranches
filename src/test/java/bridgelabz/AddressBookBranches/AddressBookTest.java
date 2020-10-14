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

import bridgelabz.AddressBookBranches.*;;

/**
 * Unit test for AddressBook.
 */
public class AddressBookTest {
	AddressBookMain addressBook;

	@Before
	public void init() {
		addressBook = AddressBookMain.getAddressBook();
		addressBook.addContactDetails(AddressBookMain.getContactDetails());
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

//    @After
//	public void remove() {
//		Path path = Paths.get("F:/demo");
//		if (Files.exists(path)) {
//			path = Paths.get("F:/demo/demo.txt");
//			if (Files.exists(path)) {
//				try {
//					Files.delete(path);
//				} catch (IOException e) {
//					System.out.println("The file does not exist");
//					e.printStackTrace();
//				}
//			}
//			path = Paths.get("F:/demo");
//			try {
//				Files.delete(path);
//			} catch (IOException e) {
//				System.out.println("The directory does not exist");
//				e.printStackTrace();
//			}
//		}
//	}
}
