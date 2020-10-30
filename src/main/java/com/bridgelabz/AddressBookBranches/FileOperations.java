package com.bridgelabz.AddressBookBranches;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperations {
	/**
	 * @return returns true if directory is created
	 */
	public static boolean createDirectory(String containingDirectory, String toBeCreated) {
		boolean flag = false;
		Path path = Paths.get(containingDirectory);
		if (Files.exists(path)) {
			path = Paths.get(toBeCreated);
			if (Files.exists(path))
				return true;
			try {
				Files.createDirectory(path);
				flag = true;
			} catch (IOException e) {
				System.out.println("The specified path does not exist");
			}
		} else
			System.out.println("The conataining directory was not found");
		return flag;
	}

	/**
	 * @return returns true if file is created
	 */
	public static boolean createFile(String containingDirectory, String toBeCreated) {
		boolean flag = false;
		Path path = Paths.get(containingDirectory);
		if (Files.exists(path)) {
			path = Paths.get(toBeCreated);
			if (Files.exists(path))
				return true;
			try {
				Files.createFile(path);
				flag = true;
			} catch (IOException e) {
				System.out.println("The path specified does not exist");
			}
		} else
			System.out.println("The conataining directory was not found");
		return flag;
	}
}