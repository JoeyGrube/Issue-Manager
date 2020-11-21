/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Tests the IssueWriter class and compares the files made with their expected files
 * @author josephgrube
 *
 */
public class IssueWriterTest {

	/**
	 * Tests the IssueWriter class
	 */
	@Test
	public void testWriteIssues() {
		ArrayList<Issue> issueList = new ArrayList<>();
		ArrayList<String> notes = new ArrayList<>();
		String n = "note 1";
		notes.add(n);
		issueList.add(new Issue(0, "New", "Bug", "Issue description", "", false, "", notes ));
		try {
		IssueWriter.writeIssuesToFile("test-files/actual_test1.txt", issueList);
		} catch(IllegalArgumentException e) {
			//Do nothing.
		}
		checkFiles("test-files/exp_issue_new.txt", "test-files/actual_test1.txt");
		IssueWriter i = new IssueWriter();
		assertFalse(i == null);
	}
	/**
	 *  Checks the file after the files are made
	 * @param expFile the expected value for the file
	 * @param actFile the actual value for the file
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new File (expFile));
			Scanner actScanner = new Scanner(new File(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
		    expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
