package edu.ncsu.csc216.issue_manager.model.manager;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.io.IssueWriter;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Manages issues
 * @author josephgrube
 *
 */
public class IssueManager {
	private IssueList issueList;
	private static IssueManager instance;
	/**
	 * Constructor
	 */
	private IssueManager() {
		issueList = new IssueList();
	}
	/**
	 * Gets instance of IssueManager
	 * @return null, later an instance of IssueManager
	 */
	public static IssueManager getInstance() {
		if(instance == null) {
			instance = new IssueManager();
		}
		return instance;
	}
	/**
	 * Saves files to a file
	 * @param fileLocation the pathway for the file
	 */
	public void saveIssuesToFile(String fileLocation) {
		IssueWriter.writeIssuesToFile(fileLocation, issueList.getIssues());

	}
	/**
	 * Loads issues from a file
	 * @param file the file name
	 */
	public void loadIssuesFromFile(String file) {
		issueList.addIssues(IssueReader.readIssuesFromFile(file));
		
	}
	/**
	 * Creates new issues
	 */
	public void createNewIssueList() {
		issueList = new IssueList();
	}
	/**
	 * Gets issue list as an array
	 * @return null, later an array of issues
	 */
	public Object[][] getIssueListAsArray(){
		Object[][] arr = new Object[issueList.getIssues().size()][4];
		for(int i = 0; i < issueList.getIssues().size(); i++) {
			arr[i][0] = issueList.getIssues().get(i).getIssueId();
			arr[i][1] = issueList.getIssues().get(i).getState();
			arr[i][2] = issueList.getIssues().get(i).getIssueType();
			arr[i][3] = issueList.getIssues().get(i).getSummary();
		}
		return arr;	
	}
	/**
	 * Gets issue list as an array that's organized by issue type
	 * @param issue the issue type
	 * @return null, later an array of issues
	 */
	public Object[][] getIssueListAsArrayByIssueType(String issue){
		Object[][] arr = new Object[issueList.getIssuesByType(issue).size()][4];
		for(int i = 0; i < issueList.getIssuesByType(issue).size(); i++) {
			arr[i][0] = issueList.getIssuesByType(issue).get(i).getIssueId();
			arr[i][1] = issueList.getIssuesByType(issue).get(i).getState();
			arr[i][2] = issueList.getIssuesByType(issue).get(i).getIssueType();
			arr[i][3] = issueList.getIssuesByType(issue).get(i).getSummary();
		}
		return arr;	
	}
	/**
	 * Returns an issue by it's id
	 * @param id the id of the issue
	 * @return null , the issue
	 */
	public Issue getIssueById(int id) {
		return issueList.getIssueById(id);
	}
	/**
	 * Deletes issue by id
	 * @param id the id of the issue
	 */
	public void deleteIssueById(int id) {
		issueList.deleteIssueById(id);
	}
	/**
	 * executes command
	 * @param i the index for the command
	 * @param c the command
	 */
	public void executeCommand(int i, Command c) {
		issueList.executeCommand(i, c);
	}
	/**
	 * Adds issue to the list
	 * @param issue the issue type
	 * @param summary summary of the issue
	 * @param note additional info on the issue
	 */
	public void addIssueToList(IssueType issue, String summary, String note) {
		issueList.addIssue(issue, summary, note);
	}
	
}
