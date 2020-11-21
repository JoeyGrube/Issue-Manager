/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
/**
 * Tests the issueList class
 * @author josephgrube
 *
 */
public class IssueListTest {

	private IssueList issueList;
	/**
	 * Sets up issueList
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.issueList = new IssueList();
		
	}

	/**
	 * Tests the issueList class
	 */
	@Test
	public void test() {
		issueList.addIssue(Issue.IssueType.ENHANCEMENT, "This could be better", "note 1");
		issueList.addIssue(Issue.IssueType.ENHANCEMENT, "This could be better", "note 2");
		issueList.addIssue(Issue.IssueType.BUG, "Don't work", "Note 1");
		assertTrue(issueList.getIssues().size() == 3);
		Command c = new Command(Command.CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		issueList.executeCommand(2, c);
		System.out.println(issueList.getIssues().get(2).getState());
		ArrayList<Issue> someIssues = new ArrayList<>();
		someIssues.add(new Issue(Issue.IssueType.BUG, "Don't work", "Note 1"));
		Issue.incrementCounter();
		someIssues.add(new Issue(Issue.IssueType.BUG, "Don't work", "Note 1"));
		assertEquals(someIssues.get(1).getIssueId(), 4);
		assertTrue(someIssues.size() == 2);
		issueList.addIssues(someIssues);
		assertTrue(issueList.getIssues().size() == 2);
		ArrayList<Issue> searchId = (ArrayList<Issue>) issueList.getIssuesByType("Bug");
		assertTrue(searchId.size() == 2);
		assertEquals(issueList.getIssues().get(1).getIssueId() , 4);
		assertTrue(issueList.getIssueById(4).getIssueType().equals("Bug"));
		issueList.deleteIssueById(4);
		assertTrue(issueList.getIssues().size() == 1);
		assertNull(issueList.getIssueById(99));
		try {
			issueList.addIssue(Issue.IssueType.BUG, "", "Ayyy");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(issueList.getIssues().size() == 1);
		}
		ArrayList<Issue> issueCase = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		issueList.addIssues(issueCase);
		c  = new Command(Command.CommandValue.REOPEN, "hhh", null, "note 3");
		issueList.getIssueById(6).update(c);
		String n = issueList.getIssueById(6).getState();
		assertEquals(n, "Working");
	}

}
