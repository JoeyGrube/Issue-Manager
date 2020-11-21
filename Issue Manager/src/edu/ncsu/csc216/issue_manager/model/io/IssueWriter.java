package edu.ncsu.csc216.issue_manager.model.io;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
/**
 * Writes the issues
 * @author josephgrube
 *
 */
public class IssueWriter {
	/**
	 * Constructor
	 */
	public IssueWriter() {
		//Does nothing
	}
	/**
	 * Writes issues to file
	 * @param name name of the file
	 * @param list the list of issues
	 * @throws IllegalArgumentException if the file cannot be written
	 */
	public static void writeIssuesToFile(String name, List<Issue> list) {
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(name));
		} catch(IOException e) {
			throw new IllegalArgumentException();
		}
		for(Issue c : list) {
			fileWriter.println(c.toString());
		}
		fileWriter.close();
	}
}
