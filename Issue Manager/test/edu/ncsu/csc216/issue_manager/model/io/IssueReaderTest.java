/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Tests the IssueReader class
 * @author josephgrube
 *
 */
public class IssueReaderTest {
	/**This file should run perfectly, have five issues at the end */
	private String fileName1 = "test-files/issue1.txt";
	/** This file will not run perfectly*/
	private String fileName2 = "test-files/issue2.txt";
	/** This file will not run perfectly*/
	private String fileName3 = "test-files/issue3.txt";
	/** This file will not run perfectly*/
	private String fileName4 = "test-files/issue4.txt";
	
	/**
	 * Tests the IssueReader class
	 */
	@Test
	public void test() {
		IssueReader i = new IssueReader();
		assertFalse(i == null);
		ArrayList<Issue> issues  = new ArrayList<Issue>();
		issues = IssueReader.readIssuesFromFile(fileName1);
		assertEquals(issues.size() , 5);
		IssueWriter.writeIssuesToFile("test-files/issueRTst", issues);
		try {
		issues  = new ArrayList<Issue>();
		issues = IssueReader.readIssuesFromFile(fileName2);
		assertEquals(issues.size() , 0);
		} catch(IllegalArgumentException e) {
			assertEquals(issues.size() , 0);
		}
		try {
		issues  = new ArrayList<Issue>();
		issues = IssueReader.readIssuesFromFile(fileName3);
		assertEquals(issues.size() , 0);
		} catch(IllegalArgumentException e) {
			assertEquals(issues.size() , 0);
		}
		try {
		issues  = new ArrayList<Issue>();
		issues = IssueReader.readIssuesFromFile(fileName4);
		assertEquals(issues.size() , 0);
		} catch(IllegalArgumentException e) {
			assertEquals(issues.size() , 0);
		}
		try {
			issues  = new ArrayList<Issue>();
			issues = IssueReader.readIssuesFromFile("test-files/issue7.txt");
			assertEquals(issues.size() , 0);
		} catch (IllegalArgumentException e) {
			assertEquals(issues.size() , 0);
		}
		try {
			issues  = new ArrayList<Issue>();
			issues = IssueReader.readIssuesFromFile("test-files/invalidFile1.txt");
			assertEquals(issues.size() , 0);
		} catch (IllegalArgumentException e) {
			assertEquals(issues.size() , 0);
		}
	}

}
