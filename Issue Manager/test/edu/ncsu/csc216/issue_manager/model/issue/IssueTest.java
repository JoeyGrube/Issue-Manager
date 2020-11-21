/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.issue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;


/**
 * Tests the Issue class
 * @author josephgrube
 *
 */
public class IssueTest {

	
	/**
	 * The test class
	 */
	@Test
	public void test() {
		Issue i = new Issue(Issue.IssueType.ENHANCEMENT, "no summary", "note 1");
		assertTrue(i.getIssueType().equals("Enhancement"));
		assertTrue(i.getSummary().equals("no summary"));
		assertTrue(i.getNotesString().equals("-note 1\n"));
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("note 1");
		notes.add("note 2");
		
		i = new Issue(616, "New", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
		assertTrue(i.getIssueId() == 616);
		assertTrue(i.getState().equals("New"));
		assertTrue(i.getIssueType().equals("Bug"));
		assertTrue(i.getSummary().equals("Bug makes it no work"));
		assertTrue(i.getOwner().equals("Jegrube"));
		assertTrue(i.isConfirmed());
		assertNull(i.getResolution());
		assertTrue(i.getNotesString().equals("-note 1\n-note 2\n"));
		i = new Issue(616, "New", "Enhancement", "Bug makes it no work", "Jegrube", false, "", notes );
		assertTrue(i.getIssueType().equals("Enhancement"));
		assertTrue(i.getNotes().size() == notes.size());
		i = new Issue(616, "Closed", "Bug", "Bug makes it no work", "Jegrube", true, "WontFix", notes );
		assertTrue(i.getState().equals("Closed"));
		try {
			i = new Issue(-1, "Closed", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(i.getState().equals("Closed"));
		}
		try {
			i = new Issue(1, "Confirmed", "Enhancement", "summary", null, false, "", notes);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(i.getState().equals("Closed"));
		}
	}
	
	/**
	 * Tests the inner state classes
	 */
	@Test
	public void stateTest() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("note 1");
		notes.add("note 2");
		Issue i = new Issue(616, "New", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
		Command c = new Command(CommandValue.ASSIGN, "Jegrube", null, "note 1");
		try {
		i.update(c);
		fail();
		} catch ( UnsupportedOperationException e) {
			assertEquals(i.getState(), "New");
		}
		c = new Command(CommandValue.ASSIGN, "Jegrube", null, "note 1");
		i = new Issue(616, "New", "Enhancement", "Bug makes it no work", "Jegrube", false, "", notes );
		i.update(c);
		assertEquals(i.getState(), "Working");
		try {
			c = new Command(CommandValue.VERIFY, "Jegrube", null, "note 1");
			i.update(c);
		} catch( UnsupportedOperationException e) {
			assertTrue(i.getState().equals("Working"));
		}
		try {
			c = new Command(CommandValue.ASSIGN, "Jegrube", null, "note 1");
			i = new Issue(616, "New", "Enhancement", "", "Jegrube", true, "", notes );
			i.update(c);
		} catch(IllegalArgumentException e) {
			assertTrue(i.getState().equals("Working"));
		}
		c = new Command(CommandValue.CONFIRM, "Jegrube", null, "note 1");
		i = new Issue(616, "New", "Bug", "Summary 1", "Jegrube", true, "", notes );
		i.update(c);
		assertEquals(i.getState(), "Confirmed");
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		i = new Issue(616, "New", "Bug", "Summary 1", "Jegrube", true, "", notes );
		i.update(c);
		assertEquals(i.getState(), "Closed");
		i = new Issue(616, "New", "Bug", "Bug makes it no work", "Jegrube", false, "", notes );
		c = new Command(CommandValue.CONFIRM, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Confirmed");
		i = new Issue(616, "New", "Enhancement", "This would be a great idea", "Jegrube", false, "", notes );
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Closed");
		//WorkingState Tests
		i = new Issue(616, "Working", "Enhancement", "This would be a great idea", "Jegrube", false, "", notes );
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Closed");
		i = new Issue(616, "Working", "Enhancement", "Bug makes it no work", "Jegrube", false, "", notes );
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.FIXED, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Verifying");
		i = new Issue(616, "Working", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Closed");
		// ConfirmedState Tests
		i = new Issue(616, "Confirmed", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
		c = new Command(CommandValue.RESOLVE, "Jegrube", Resolution.WONTFIX, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Closed");
		i = new Issue(616, "Confirmed", "Bug", "Bug makes it no work", "Jegrube", true, "", notes );
		c = new Command(CommandValue.ASSIGN, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Working");
		// VerifyingState Tests
		i = new Issue(616, "Verifying", "Bug", "Bug makes it no work", "Jegrube", true, "Fixed", notes );
		c = new Command(CommandValue.REOPEN, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Working");
		i = new Issue(616, "Verifying", "Bug", "Bug makes it no work", "Jegrube", true, "Fixed", notes );
		c = new Command(CommandValue.VERIFY, "Jegrube", Resolution.FIXED, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Closed");
		//ClosedState Tests
		i = new Issue(616, "Closed", "Bug", "Bug makes it no work", "Jegrube", true, "WontFix", notes );
		c = new Command(CommandValue.REOPEN, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Working");
		i = new Issue(616, "Closed", "Enhancement", "This would be a great idea", "Jegrube", false, "WontFix", notes );
		c = new Command(CommandValue.REOPEN, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "Working");
		i = new Issue(616, "Closed", "Enhancement", "This would be a great idea", "", false, "WontFix", notes );
		c = new Command(CommandValue.REOPEN, "Jegrube", null, "note 1");
		i.update(c);
		assertEquals(i.getState(), "New");
		
		i = new Issue(6, "Closed", "Enhancement", "This would be a great idea", "Jegrube", false, "WontFix", notes );
		c = new Command(CommandValue.REOPEN, "", null, "note 1");
		i.update(c);
		assertEquals("Working", i.getState());
		
		
		
		
	}

}
