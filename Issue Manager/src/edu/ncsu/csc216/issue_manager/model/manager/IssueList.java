package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * List of the issues
 * @author josephgrube
 *
 */
public class IssueList {
	/**
	 * Constructor
	 */
	private ArrayList<Issue> issueList;
	private Issue i;
	
	/**
	 * Constructor for IssueList
	 */
	public IssueList() {
		issueList = new ArrayList<>();
		Issue.setCounter(0);
	}
	
	/**
	 * Adds issue
	 * @param issue the issue being added
	 * @param summary the summary of the issue
	 * @param note additional info on the issue
	 * @return the issues index
	 * @throws IllegalArgumentException if an issue cannot be constructed
	 */
	public int addIssue(IssueType issue, String summary, String note) {
		
	try {
		i = new Issue(issue, summary, note);
		issueList.add(i);
		Issue.incrementCounter();
	} catch(IllegalArgumentException e) {
		throw new IllegalArgumentException();
	}
		return i.getIssueId();
	}
	/**
	 * Adds issue
	 * @param issue a list of issues to add
	 */
	public void addIssues(List<Issue> issue) {
		issueList = new ArrayList<>();
		for(int j = 0; j < issue.size(); j++) {
			issueList.add(issue.get(j));
			if(issue.get(j).getIssueId() > issueList.size() - 1) {
				Issue.setCounter(issue.get(j).getIssueId() + 1);
			}
			else {
				Issue.incrementCounter();
			}
			
		}
	}
	/**
	 * Gets issues
	 * @return null the list of issues
	 */
	public List<Issue> getIssues(){
		return issueList;
	}
	/**
	 * Gets issues by the type given
	 * @param type the issue type the user wants to get an ArrayList for
	 * @return nIssue the new ArrayList of issues
	 */
	public List<Issue> getIssuesByType(String type){
		if(type == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Issue> nIssue = new ArrayList<>();
		for(int j = 0; j < issueList.size(); j++) {
			if(issueList.get(j).getIssueType().equals(type)) {
				nIssue.add(issueList.get(j));
			}
		}
		return nIssue;
	}
	/**
	 * Gets issue by id
	 * @param id the id of the issue
	 * @return issueList.get(j) the issue with the specified id
	 */
	public Issue getIssueById(int id) {
		for(int j = 0; j < issueList.size(); j++) {
			if(issueList.get(j).getIssueId() == id) {
				return issueList.get(j);
			}
		}
		return null;
	}
	/**
	 * executes command
	 * @param j the index for the command
	 * @param c the command
	 */
	public void executeCommand(int j, Command c) {
		if(j > -1 && getIssueById(j) != null) {
			getIssueById(j).update(c);
		}

	}
	/**
	 * Deletes issue by id
	 * @param id the issue id
	 */
	public void deleteIssueById(int id) {
		
		for(int j = 0; j < issueList.size(); j++) {
			if(issueList.get(j).getIssueId() == id) {
				issueList.remove(j);
			}	
		}

	}
}

