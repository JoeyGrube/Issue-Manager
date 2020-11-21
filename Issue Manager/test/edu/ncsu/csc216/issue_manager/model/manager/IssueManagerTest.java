package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
/**
 * Tests the IssueManager class for errors
 * @author josephgrube
 *
 */
public class IssueManagerTest {

	

	/**
	 * Tests the IssueManager class
	 */
	@Test
	public void test() {
	IssueManager.getInstance().createNewIssueList();
	IssueManager.getInstance().addIssueToList(Issue.IssueType.BUG, "Buggy", "Note 1");
	Issue i = IssueManager.getInstance().getIssueById(0);
	assertTrue(i != null);
	assertTrue(i.getIssueId() == 0);
	IssueManager.getInstance().createNewIssueList();
	IssueManager.getInstance().loadIssuesFromFile("test-files/issue1.txt");
	i = IssueManager.getInstance().getIssueById(1);
	assertEquals(i.getState(), "New");
	Object[][] obj = IssueManager.getInstance().getIssueListAsArray();
	assertEquals(obj[0][0], 1);
	obj = IssueManager.getInstance().getIssueListAsArrayByIssueType("Bug");
	assertEquals(obj[0][0], 2);
	IssueManager.getInstance().createNewIssueList();
	IssueManager.getInstance().addIssueToList(Issue.IssueType.BUG, "Issue Desc.", "Note 1");
	IssueManager.getInstance().saveIssuesToFile("test-files/test616.txt");
	IssueManager.getInstance().createNewIssueList();
	IssueManager.getInstance().loadIssuesFromFile("test-files/issue1.txt");
	Command c = new Command(CommandValue.REOPEN, "", null, "note");
	IssueManager.getInstance().executeCommand(6, c);
	assertEquals(IssueManager.getInstance().getIssueById(6).getState(), "Working");
	IssueManager.getInstance().deleteIssueById(1);
	assertNull(IssueManager.getInstance().getIssueById(1));
	}

}
