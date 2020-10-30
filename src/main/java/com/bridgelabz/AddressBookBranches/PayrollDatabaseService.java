package com.bridgelabz.AddressBookBranches;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bridgelabz.repos.Repos;

/**
 * Provides service for employee payroll database
 * 
 * @author Shubham
 */
public class PayrollDatabaseService {
	Logger logger = null;
	private PreparedStatement preparedStatement;
	private static PayrollDatabaseService pdService;

	/**
	 * @return the preparedStatement
	 */
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	private PayrollDatabaseService() {
		logger = LogManager.getLogger();
	}

	public static PayrollDatabaseService getInstance() {
		if (pdService == null)
			pdService = new PayrollDatabaseService();
		return pdService;
	}

	public void loadDriver() throws JDBCException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			logger.info("Driver Loaded");
		} catch (ClassNotFoundException e1) {
			throw new JDBCException("Error while loading Driver " + e1.getMessage());
		}
	}

	public void listOfDrivers() {
		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = driverList.nextElement();
			System.out.println("	" + driverClass.getClass().getName());
		}
	}

	public Connection connectToDatabase(Connection connection) throws JDBCException {
		try {
			logger.info("Connecting to database");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/addressbook_service?useSSL=false", "root",
					Repos.returnPassword());
			logger.info("Connection Established " + connection);
		} catch (SQLException e) {
			throw new JDBCException("Error while connecting to " + connection + e.getMessage());
		}
		return connection;
	}

	public List<ContactDetails> getListFromDatabase(Connection connection) throws JDBCException {
		ResultSet result = null;
		List<ContactDetails> listContactDetails = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("select * from contacts");
			result = preparedStatement.executeQuery();			
			listContactDetails = new ArrayList<>();
			while (result.next()) {
				ContactDetails contactDetails = new ContactDetails();
				contactDetails.setFirstName(result.getString(1));
				contactDetails.setAddress(result.getString(2));
				contactDetails.setLastName(result.getString(5));
				contactDetails.setEmail(result.getString(4));
				long phoneNumber = (long)result.getDouble(3);
				contactDetails.setPhoneNumber(Long.toString(phoneNumber));
				listContactDetails.add(contactDetails);
			}
			preparedStatement = connection.prepareStatement("select c.first_name,zip_code,city,state from contacts as c,contacts_address as cd,address as a where c.first_name = cd.first_name and cd.zip = a.zip_code");
			result = preparedStatement.executeQuery();			
			while (result.next()) {
				String firstName = result.getString(1);
				for(ContactDetails cd:listContactDetails) {
					if(cd.getFirstName().equals(firstName)) {
						cd.setZip((int)result.getDouble(2));
						cd.setCity(result.getString(3));
						cd.setState(result.getString(4));
						break;
					}
				}
			}
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("List successfully retrieved from database");
		} catch (SQLException exception) {
			throw new JDBCException("Error while retrieving data");
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (SQLException e) {
				throw new JDBCException(
						"Error while closing resources when retrieving data" + connection + e.getMessage());
			}
		}
		return listContactDetails;
	}

	public void updateDetailsPrepared(Connection connection,String field,String value,String record) throws JDBCException {
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("Update contacts set address = ? where first_name = ?");
//			preparedStatement.setString(1, field);
			preparedStatement.setString(1, value);
			preparedStatement.setString(2, record);
			preparedStatement.execute();
			connection.commit();
			connection.setAutoCommit(true);
			preparedStatement.close();
		} catch (SQLException exception) {
			throw new JDBCException("Error while updating with prepared Statement " + exception.getMessage());
		}
	}

	public List<ContactDetails> getDateRange(Connection connection, Date date1, Date date2) throws JDBCException {
		ResultSet result = null;
		List<ContactDetails> listContactDetails = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"Select * from contacts where date_added between cast(? as date) and cast(? as date)");
			preparedStatement.setDate(1, date1);
			preparedStatement.setDate(2, date2);
			result = preparedStatement.executeQuery();
			listContactDetails = new ArrayList<>();
			while (result.next()) {
				ContactDetails contactDetails = new ContactDetails();
				contactDetails.setFirstName(result.getString(1));
				contactDetails.setAddress(result.getString(2));
				contactDetails.setLastName(result.getString(5));
				contactDetails.setEmail(result.getString(4));
				long phoneNumber = (long)result.getDouble(3);
				contactDetails.setPhoneNumber(Long.toString(phoneNumber));
				listContactDetails.add(contactDetails);
			}
			preparedStatement = connection.prepareStatement("select c.first_name,zip_code,city,state from contacts as c,contacts_address as cd,address as a where c.first_name = cd.first_name and cd.zip = a.zip_code and c.date_added between cast(? as date) and cast(? as date)");
			preparedStatement.setDate(1, date1);
			preparedStatement.setDate(2, date2);
			result = preparedStatement.executeQuery();			
			while (result.next()) {
				String firstName = result.getString(1);
				for(ContactDetails cd:listContactDetails) {
					if(cd.getFirstName().equals(firstName)) {
//						System.out.println(firstName);
						cd.setZip((int)result.getDouble(2));
						cd.setCity(result.getString(3));
						cd.setState(result.getString(4));
						break;
					}
				}
			}
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("List successfully retrieved from database");
		} catch (SQLException exception) {
			throw new JDBCException("Error while retrieving data");
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (SQLException e) {
				throw new JDBCException(
						"Error while closing resources when retrieving data" + connection + e.getMessage());
			}
		}
		return listContactDetails;
	}

	public List<ContactDetails> getCityContacts(Connection connection, String city) throws JDBCException {
		ResultSet result = null;
		List<ContactDetails> listContactDetails = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"select c.first_name,c.last_name,c.address,c.phone_number,c.email,a.zip_code,a.city,a.state from"
					+ " contacts as c, address as a,contacts_address as ca where a.city = ? and c.first_name = ca.first_name"
					+ " and ca.zip = a.zip_code;");
			preparedStatement.setString(1, city);
			result = preparedStatement.executeQuery();
			listContactDetails = new ArrayList<>();
			while (result.next()) {
				ContactDetails contactDetails = new ContactDetails();
				contactDetails.setFirstName(result.getString(1));
				contactDetails.setAddress(result.getString(3));
				contactDetails.setLastName(result.getString(2));
				contactDetails.setEmail(result.getString(5));
				long phoneNumber = (long)result.getDouble(4);
				contactDetails.setPhoneNumber(Long.toString(phoneNumber));
				contactDetails.setZip((int)result.getDouble(6));
				contactDetails.setCity(result.getString(7));
				contactDetails.setState(result.getString(8));
				listContactDetails.add(contactDetails);
			}
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("List successfully retrieved from database");
		} catch (SQLException exception) {
			throw new JDBCException("Error while retrieving data");
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (SQLException e) {
				throw new JDBCException(
						"Error while closing resources when retrieving data" + connection + e.getMessage());
			}
		}
		return listContactDetails;
	}

	public List<ContactDetails> getStateContacts(Connection connection, String state) throws JDBCException {
		ResultSet result = null;
		List<ContactDetails> listContactDetails = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"select c.first_name,c.last_name,c.address,c.phone_number,c.email,a.zip_code,a.city,a.state from"
					+ " contacts as c, address as a,contacts_address as ca where a.state = ? and c.first_name = ca.first_name"
					+ " and ca.zip = a.zip_code;");
			preparedStatement.setString(1, state);
			result = preparedStatement.executeQuery();
			listContactDetails = new ArrayList<>();
			while (result.next()) {
				ContactDetails contactDetails = new ContactDetails();
				contactDetails.setFirstName(result.getString(1));
				contactDetails.setAddress(result.getString(3));
				contactDetails.setLastName(result.getString(2));
				contactDetails.setEmail(result.getString(5));
				long phoneNumber = (long)result.getDouble(4);
				contactDetails.setPhoneNumber(Long.toString(phoneNumber));
				contactDetails.setZip((int)result.getDouble(6));
				contactDetails.setCity(result.getString(7));
				contactDetails.setState(result.getString(8));
				listContactDetails.add(contactDetails);
			}
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("List successfully retrieved from database");
		} catch (SQLException exception) {
			throw new JDBCException("Error while retrieving data");
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (SQLException e) {
				throw new JDBCException(
						"Error while closing resources when retrieving data" + connection + e.getMessage());
			}
		}
		return listContactDetails;
	}

	public void addContact(Connection connection, ContactDetails contactDetails) throws JDBCException {
		List<ContactDetails> listContactDetails = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("insert into contacts values(?,?,?,?,?,?)");
			preparedStatement.setString(1, contactDetails.getFirstName());
			preparedStatement.setString(2, contactDetails.getAddress());
			preparedStatement.setLong(3, Long.parseLong(contactDetails.getPhoneNumber()));
			preparedStatement.setString(4, contactDetails.getEmail());
			preparedStatement.setString(5, contactDetails.getLastName());
			preparedStatement.setDate(6, Date.valueOf("2020-08-15"));
			preparedStatement.execute();
			try {
				preparedStatement = connection.prepareStatement("insert into address values(?,?,?)");
				preparedStatement.setDouble(1, contactDetails.getZip());
				preparedStatement.setString(2, contactDetails.getCity());
				preparedStatement.setString(3, contactDetails.getState());
				preparedStatement.execute();
			}
			catch(SQLException exception) {
				logger.info("Data already present inside address table " + exception.getMessage());
			}
			preparedStatement = connection.prepareStatement("insert into contacts_address values(?,?)");
			preparedStatement.setString(1, contactDetails.getFirstName());
			preparedStatement.setDouble(2, contactDetails.getZip());
			preparedStatement.execute();
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("List successfully retrieved from database");
		} catch (SQLException exception) {
			try {
			connection.rollback();
		} catch (SQLException e) {
			throw new JDBCException("Error when trying to perform rollback on connection " + e.getMessage());
		}
		throw new JDBCException("Error while inserting data into multiple tables with prepared Statement " + exception.getMessage());
		} 
	}
}
