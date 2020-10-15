/**
 * 
 */
package bridgelabz.AddressBookBranches;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author Shubham, this class is used for writing to CSV File.
 *
 */
public class OpenCSVWriter {
	public static boolean writeToCSV()
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		List<ContactDetails> addressBook = new ArrayList();
		addressBook.add(new ContactDetails("Shubham","Mittal", "302", "K", "H", 21, "1245341212", "gmail.com"));
		addressBook.add(new ContactDetails("Shubham","Mittal", "302", "K", "H", 21, "1245341212", "gmail.com"));
		Writer writer = Files.newBufferedWriter(Paths.get("F:/demo/demo.csv"));
		StatefulBeanToCsv<ContactDetails> beanToCsv = new StatefulBeanToCsvBuilder<ContactDetails>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
		ColumnPositionMappingStrategy<ContactDetails> mappingStrategy= new ColumnPositionMappingStrategy<ContactDetails>();
		mappingStrategy.setType(ContactDetails.class);
		String []columns = new String[] {"firstName","lastName","address","city","state","zip","phoneNumber","email"};
		mappingStrategy.setColumnMapping(columns);
		try {
			System.out.println(addressBook);
			beanToCsv.write(addressBook);
		}catch(Exception exception) {
			System.out.println("Exception occured while writing");
		}
		return true;
	}

	public static boolean readFromCSV() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get("F:/demo/demo.csv"));
			CSVReader csvReader = new CSVReader(reader);
			String []nextRecord;
			while((nextRecord = csvReader.readNext()) != null) {
				System.out.println("Name " + nextRecord[0]);
				System.out.println("Email " + nextRecord[1]);
				System.out.println("Number " + nextRecord[2]);
				System.out.println("Country " + nextRecord[3]);
			}
			/**
			 * Alternate Implementation
			 */
//			List<String[]> records = csvReader.readAll();
//			for(int i=0;i<records.size();i++) {
//				String []nextRecord = records.get(i);
//				System.out.println("Name " + nextRecord[0]);
//				System.out.println("Email " + nextRecord[1]);
//				System.out.println("Number " + nextRecord[2]);
//				System.out.println("Country " + nextRecord[3]);
//			}
		} catch (IOException e) {
			System.out.println("Error while reading from CSV File");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean readFromCSVUsingPOJO() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get("F:/demo/demo.csv"));
			CsvToBean<ContactDetails> csvToBean = new CsvToBeanBuilder(reader).withType(ContactDetails.class).withIgnoreLeadingWhiteSpace(true).build();
			/**
			 * Alternate Implementation
			 */
//			Iterator<ContactDetails> iterator =  csvToBean.iterator();
//			while(iterator.hasNext()) {
//				ContactDetails contactDetails = iterator.next();
//				System.out.println("First Name :" + contactDetails.getFirstName());
//				System.out.println("Last Name :" + contactDetails.getLastName());
//				System.out.println("Address :" + contactDetails.getAddress());
//				System.out.println("City :" + contactDetails.getCity());
//				System.out.println("State :" + contactDetails.getState());
//				System.out.println("Zip :" + contactDetails.getZip());
//				System.out.println("Phone Number :" + contactDetails.getPhoneNumber());
//				System.out.println("Email :" + contactDetails.getEmail());
//			}
			List<ContactDetails> records = csvToBean.parse();
			for(int i=0;i<records.size();i++) {
				ContactDetails contactDetails = records.get(i);
				System.out.println("First Name :" + contactDetails.getFirstName());
				System.out.println("Last Name :" + contactDetails.getLastName());
				System.out.println("Address :" + contactDetails.getAddress());
				System.out.println("City :" + contactDetails.getCity());
				System.out.println("State :" + contactDetails.getState());
				System.out.println("Zip :" + contactDetails.getZip());
				System.out.println("Phone Number :" + contactDetails.getPhoneNumber());
				System.out.println("Email :" + contactDetails.getEmail());
			}
		} catch (IOException e) {
			System.out.println("Error while reading from CSV File");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
