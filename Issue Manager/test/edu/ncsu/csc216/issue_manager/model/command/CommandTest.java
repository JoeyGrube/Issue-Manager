/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;


/**
 * Tests the Command class
 * @author josephgrube
 *
 */
public class CommandTest {

	
	/**
	 * The test for the Command class
	 */
	@Test
	public void test() {
		String owner = "Jegrube";
		String note = "N/A";
		Command c = new Command(Command.CommandValue.ASSIGN, owner, null, note);
		assertTrue(c.getOwnerId().equals("Jegrube"));
		assertTrue(c.getResolution() == null);
		assertTrue(c.getCommand() == Command.CommandValue.ASSIGN);
		assertTrue(c.getNote().equals("N/A"));
		try {
			c = new Command(Command.CommandValue.ASSIGN, "", null, note);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(Command.CommandValue.ASSIGN, null, null, note);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(null, "N/A", null, note);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(null, "", null, note);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(Command.CommandValue.RESOLVE, "Jegrube", null, note);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(Command.CommandValue.RESOLVE, "Jegrube", Command.Resolution.WONTFIX, null);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		try {
			c = new Command(Command.CommandValue.RESOLVE, "Jegrube", Command.Resolution.WONTFIX, "");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(c.getOwnerId().equals("Jegrube"));
		}
		
	}

}
