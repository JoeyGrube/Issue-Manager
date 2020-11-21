package edu.ncsu.csc216.issue_manager.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;
/**
 * Reads issues
 * @author josephgrube
 *
 */
public class IssueReader {
	/**
	 * Constructor
	 */
	public IssueReader() {
		//Doesn't do anything
	}
	/**
	 * Read issues from file
	 * @param file the string that contains the file name
	 * @return  issues the ArrayList with issues
	 * @throws IllegalArgumentException is the file cannot be read or the file cannot be found
	 */
	public static ArrayList<Issue> readIssuesFromFile(String file){
	Scanner fileReader = null;
	try {
		 fileReader = new Scanner(new File(file));
	} catch(FileNotFoundException e) {
		throw new IllegalArgumentException("No file found");
	}
		fileReader.useDelimiter("\\n[*]");
		String h = "";
		ArrayList<Issue> issues = new ArrayList<Issue>();
		
		while(fileReader.hasNext()) {
			try {
				h = fileReader.next();
				Issue i = processIssue(h);
				issues.add(i);
			
			} catch(IllegalArgumentException e) {
				fileReader.close();
				throw new IllegalArgumentException();
			}
		}
		fileReader.close();
		return issues;
	}
	/**
	 * Processes issues
	 * @param issue issue being examined
	 * @return issue the constructed issue object
	 */
	private static Issue processIssue(String issue) {
		if(issue.startsWith("*")) {
			issue = issue.substring(1);
		}
		Scanner fileLine = new Scanner(issue);
		fileLine.useDelimiter("[+]");
		
		if(!fileLine.hasNextInt()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		int id = fileLine.nextInt();
		System.out.println(id);
		if(id < 0) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		if(!fileLine.hasNext()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		String state = fileLine.next();
		
		if(!fileLine.hasNext()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		String issueType = fileLine.next();
		if(!fileLine.hasNext()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		String summary = fileLine.next();
		System.out.println(summary);
		if(!fileLine.hasNext()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		String owner = fileLine.next();
		
		if(!fileLine.hasNextBoolean()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		boolean confirmed = fileLine.nextBoolean();
		System.out.println(confirmed);
		if(!fileLine.hasNext()) {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		String resolution = fileLine.next();
		int idx = 0;
		String n = "";
		
		if(resolution.contains("-")){
			idx = resolution.indexOf("-");
			n = resolution.substring(idx + 1);
			resolution = resolution.substring(0, idx);
			resolution = resolution.trim();	
		}
		else {
			fileLine.close();
			throw new IllegalArgumentException();
		}
		
		String notes = n;
		Scanner notesScanner = new Scanner(notes);
		notesScanner.useDelimiter("\\r\\n[-]");
		ArrayList<String> note = new ArrayList<>();

		while(notesScanner.hasNext()) {
			String ns = notesScanner.next();
			ns = ns.replace("\\n", "");
			ns = ns.replaceAll("\\t", "");
			ns = ns.replaceAll("\\r", "");
			ns = ns.trim();
			note.add(ns);
			
		}
		
		notesScanner.close();
		fileLine.close();
		Issue i = null;

		
		i = new Issue(id, state, issueType, summary, owner, confirmed, resolution, note);
		
		System.out.print(i.getNotesString());
		return i;
	}
}
