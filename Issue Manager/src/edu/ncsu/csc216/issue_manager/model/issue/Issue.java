/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.issue;



import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Contains information for issues
 * @author josephgrube
 *
 */
public class Issue {
	/** One of the possible issue types*/
	public static final String I_ENHANCEMENT = "Enhancement";
	/** One of the possible issue types*/
	public static final String I_BUG = "Bug";
	/** issue ID*/
	private int issueId;
	/** summary of the issue*/
	private String summary;
	/** who's tasked with fixing it*/
	private String owner;
	/** whether or not the issue is confirmed*/
	private boolean confirmed;
	/** notes about the issue*/
	private ArrayList<String> notes;
	/** Name of potential state*/
	public static final String NEW_NAME = "New"; 
	/** Name of potential state*/
	public static final String WORKING_NAME = "Working";
	/** Name of potential state*/
	public static final String CONFIRMED_NAME = "Confirmed";
	/** Name of potential state*/
	public static final String VERIFYING_NAME = "Verifying";
	/** Name of potential state*/
	public static final String CLOSED_NAME = "Closed";
	/** Number of issues*/
	private static int counter = 0;
	/** String containing the status of the state*/
	private String state;
	/** Holds the issue type*/
	private String issueType;
	/** The resolution of the issue once it has been closed*/
	private String resolution;
	/** The NewState class, which updates the state when it's at the New state*/
	private NewState newState = new NewState();
	/** The VerifyingState class, which updates the state when it's at the Verifying state*/
	private VerifyingState verifyingState = new VerifyingState();
	/** The WorkingState class, which updates the state when it's at the Working state*/
	private WorkingState workingState = new WorkingState();
	/** The ClosedState class, which updates the state when it's at the Closed state*/
	private ClosedState closedState = new ClosedState();
	/** The ConfirmdState class, which updates the state when it's at the Confirmed state*/
	private ConfirmedState confirmedState = new ConfirmedState();	
	
	/**
	 * Constructor for Issue
	 * @param issue , the type of issue
	 * @param summary , summary of the issue
	 * @param note , notes on the issue
	 * @throws IllegalArgumentException if any parameter is invalid
	 */
	public Issue(IssueType issue, String summary, String note) {
			if(note == null || note.equals("")) {
				throw new IllegalArgumentException();
			}
			setSummary(summary);
			this.notes = new ArrayList<String>();
			notes.add(note);
			if(issue == IssueType.ENHANCEMENT) {
				setIssueType("Enhancement");
				}
			else if(issue == IssueType.BUG) {
				setIssueType("Bug");		
			}
			else {
				throw new IllegalArgumentException();
			}
			if(state == null) {
			setState("New");
			}
			setIssueId(counter);
			
		}
	
	/**
	 * Constructor for Issue
	 * @param issueId the id of the issue
	 * @param state the state of the issue
	 * @param issueType the type of issue
	 * @param summary summary of the issue
	 * @param owner the person tasked with fixing it
	 * @param confirmed whether or not the issue is confirmed to exist
	 * @param resolution has it been solved, and how so
	 * @param notes , general background notes on the issue
	 * @throws IllegalArgumentException if parameter is invalid, or an 
	 * invalid combination of parameters occur
	 */
	public Issue(int issueId, String state, String issueType, String summary, String owner,
			boolean confirmed, String resolution, ArrayList<String> notes) {
		
		if((state == null || state.equals("") || state.equals("Working") || state.equals("Verifying")) && (owner == null || owner.equals(""))) {
			throw new IllegalArgumentException();
		}
		if((state.equals("New")) && !resolution.equals("")) {
			throw new IllegalArgumentException(resolution);
		}
		if(state.equals("Confirmed") && (issueType.equals("Enhancement") || !confirmed )) {
			throw new IllegalArgumentException();
		}
		if((state.equals("Working") || state.equals("Verifying")) && issueType.equals("Bug") && !confirmed) {
			throw new IllegalArgumentException();
		}
		if(state.equals("Closed") && (resolution == null || resolution.equals(""))) {
			throw new IllegalArgumentException();
		}
		if(resolution == null) {
			throw new IllegalArgumentException();
		}
		if(issueType.equals("Enhancement") && confirmed) {
			throw new IllegalArgumentException();		
		}
		if(notes == null || notes.size() == 0) {
			throw new IllegalArgumentException();
		}
		if(issueType.equals("Enhancement") && resolution.equals("WorksForMe")) {
			throw new IllegalArgumentException();
		}
		if(state.equals("Confirmed") && !resolution.equals("")) {
			throw new IllegalArgumentException();
		}
		if(state.equals("Verifying") && !(resolution.equals("Fixed"))) {
			throw new IllegalArgumentException();
		}
		if(issueId < 0) {
			throw new IllegalArgumentException();
		}
		if(issueId > counter) {
			setCounter(issueId + 1);
		}
		setIssueId( issueId);
		setSummary(summary);
		setOwner(owner);
		setConfirmed(confirmed);
		setResolution(resolution);
		setNotes(notes);
		setIssueType(issueType);
		setState(state);
		
		
		}
	/**
	 * Set's the issue type
	 * @param type the issue type being set
	 * @throws IllegalArgumentException when the issue type is
	 * neither bug or enhancement
	 */
	private void setIssueType(String type) {
		if(type.equals("Bug") || type.equals("Enhancement")) {
			this.issueType = type;
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	/**
	 * Sets the id
	 * @param issueId the id being set
	 * @throws IllegalArgumentException whenever the issueId is negative
	 */
	private void setIssueId(int issueId) {
		if(issueId < 0) {
			throw new IllegalArgumentException();
		}
		this.issueId = issueId;
	}
	/**
	 * Sets the state
	 * @param state the state being set
	 * @throws IllegalArgumentException whenever a state is invalid
	 */
	private void setState(String state) {
			if(!(state.equals("Confirmed") || state.equals("Verifying") || state.equals("Closed") 
					|| state.equals("Working") || state.equals("New"))) {
				throw new IllegalArgumentException();
			}
			if(!isConfirmed() && getResolution() == null && getOwner() == null) {
				this.state = "New";
			}
			this.state = state;	
	}
	/**
	 * Sets the summary
	 * @param summary the summary being set
	 * @throws IllegalArgumentException if summary is empty
	 */
	private void setSummary(String summary) {
		if(summary == null || summary.equals("")) {
			throw new IllegalArgumentException();
		}
		this.summary = summary;
	}
	/**
	 * Sets the owner
	 * @param owner the owner being set
	 */
	private void setOwner(String owner) {
		if(owner == null || owner.equals("")) {
			owner = null;
		}
		this.owner = owner;
	}
	/**
	 * Sets whether or not the issue is confirmed
	 * @param confirmed the confirmation status being set
	 */
	private void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	/**
	 * Resolution of the Issue being set
	 * @param resolution the solution that's being set
	 * @throws IllegalArgumentException if the resolution is invalid
	 */
	private void setResolution(String resolution) {
		if(!(resolution.equals(Command.R_DUPLICATE) || resolution.equals(Command.R_FIXED) || resolution.equals(Command.R_WONTFIX)
				|| resolution.equals(Command.R_WORKSFORME) || resolution.equals(""))) {
			throw new IllegalArgumentException();
			}
		if(resolution.equals("")) {
			this.resolution = null;
		}
		this.resolution = resolution;
	}
		
	
	/**
	 * Sets the notes given about the issue
	 * @param notes the notes being set
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}
	/**
	 * Gives information about how many times that issue's been reported
	 */
	public static void incrementCounter() {
		counter++;
		
	}
	/**
	 * gets issue id
	 * @return issueId the number given to the issue
	 */
	public int getIssueId() {
		return issueId;
	}
	/**
	 * Gets issue type
	 * @return issueType the issue type
	 */
	public String getIssueType() {
		return issueType;
	}
	/**
	 * Gets the state
	 * @return state the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * Gets resolution
	 * @return resolution the resolution
	 */
	public String getResolution() {
		if(resolution == null || resolution.equals("")) {
			return null;
		}
		return resolution;
		
	}
	/**
	 * gets summary
	 * @return summary the summary of the issue
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * gets the owner
	 * @return owner the person working on the issue
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * gets confirmed
	 * @return confirmed the confirmation status of the issue
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
	/**
	 * gets notes
	 * @return notes the array of notes about the issue
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	/**
	 * Gets notes
	 * @return note the formatted notes string
	 */
	public String getNotesString() {
		String note = "-";
		for(int i = 0; i < notes.size(); i++) {
			note += notes.get(i) +  "\n";
			if(i < notes.size() - 1) {
				note +=  "-";
			}
		}
		return note;
	}

	@Override
	public String toString() {
		String h = "";
		if(resolution == null) {
			 h = "*" + issueId  + "+"  + state + "+" + issueType + "+" + summary + "+" + owner + "+" +  confirmed + "+"
					+ "" +	"\n" +  getNotesString();
		}
		else {
		 h = "*" + issueId  + "+"  + state + "+" + issueType + "+" + summary + "+" + owner + "+" +  confirmed + "+"
		+ resolution +	"\n" +  getNotesString();
		}
		
		return h;
	}
	/**
	 * updates command
	 * @param c the new command
	 */
	public void update(Command c) {
		
			
		int stateNum = -1;
		final int newStateNum = 0;
		final int workingStateNum = 1;
		final int confirmedStateNum = 2;
		final int verifyingStateNum = 3;
		final int closedStateNum = 4;
		if(getState().equals("New")) {
			stateNum = newStateNum;
		}
		if(getState().equals("Working")) {
			stateNum = workingStateNum;			
		}
		if(getState().equals("Confirmed")) {
			stateNum = confirmedStateNum;		
		}
		if(getState().equals("Verifying")) {
			stateNum = verifyingStateNum;		
		}
		if(getState().equals("Closed")) {
			stateNum = closedStateNum;
		}
		
	
		switch(stateNum) {
		case newStateNum:
			newState.updateState(c);
			if(newState.getStateName().equals("Working")) {
				stateNum = workingStateNum;
			}
			if(newState.getStateName().equals("Confirmed")) {
				stateNum = confirmedStateNum;
			}
			if(newState.getStateName().equals("Closed")) {
				stateNum = closedStateNum;
			}
			break;
		case workingStateNum:
			workingState.updateState(c);
			if(workingState.getStateName().equals("Verifying")) {
				stateNum = verifyingStateNum;
			}
			if(workingState.getStateName().equals("Closed")) {
				stateNum = closedStateNum;
			}
			break;
		case confirmedStateNum:
			confirmedState.updateState(c);
			if(confirmedState.getStateName().equals("Closed")) {
				stateNum = closedStateNum;
			}
			if(confirmedState.getStateName().equals("Working")) {
				stateNum = workingStateNum;
			}
			break;
		case verifyingStateNum:
			verifyingState.updateState(c);
			if(verifyingState.getStateName().equals("Closed")) {
				stateNum = closedStateNum;
			}
			if(verifyingState.getStateName().equals("Working")) {
				stateNum = workingStateNum;
			}
			break;
		case closedStateNum:
			closedState.updateState(c);
			if(closedState.getStateName().equals("Working")) {
				stateNum = workingStateNum;
			}
			break;
		default : 
			break;
		}
		
	
	}
		
	
	/**
	 * Sets the counter
	 * @param count the amount of times the issue has appeared
	 */
	public static void setCounter(int count) {
		counter = count;
	}

	/**
	 * Represents possible state for issue
	 * @author josephgrube
	 */
	public class NewState implements IssueState {
		/**
		 * Constructor
		 */
		private NewState() {
			// Does nothing
		}
		/**
		 * Updates state
		 * @param c the command the state is changing to
		 * @throws UnsupportedOperationException if the state operation is illegal
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.ASSIGN && Issue.this.getIssueType().equals("Enhancement")) {
					Issue.this.setState("Working");
					Issue.this.getNotes().add(c.getNote());	
					Issue.this.setOwner(c.getOwnerId());
				
			}
			if(c.getCommand() == CommandValue.CONFIRM && Issue.this.getIssueType().equals("Bug")) {
				Issue.this.setState("Confirmed"); 
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setConfirmed(true);
			}
			if(c.getCommand() == CommandValue.RESOLVE && ((c.getResolution() == Resolution.WONTFIX) || (c.getResolution() == Resolution.DUPLICATE) || (c.getResolution() == Resolution.WORKSFORME))
					&& Issue.this.getIssueType().equals("Bug")){
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				if(c.getResolution() == Resolution.WONTFIX){
					Issue.this.setResolution("WontFix");
				}
				if(c.getResolution() == Resolution.DUPLICATE){
					Issue.this.setResolution("Duplicate");
				}
				if(c.getResolution() == Resolution.WORKSFORME){
					Issue.this.setResolution("WorksForMe");
				}
			}
			if(c.getCommand() == CommandValue.RESOLVE && ((c.getResolution() == Resolution.WONTFIX) || (c.getResolution() == Resolution.DUPLICATE))
				&&  Issue.this.getIssueType().equals("Enhancement")) {
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				if(c.getResolution() == Resolution.WONTFIX){
					Issue.this.setResolution("WontFix");
				}
				if(c.getResolution() == Resolution.DUPLICATE){
					Issue.this.setResolution("Duplicate");
				} 
			}
			if(!(c.getCommand() == CommandValue.ASSIGN && Issue.this.getIssueType().equals("Enhancement")) 
					&& !(c.getCommand() == CommandValue.CONFIRM && Issue.this.getIssueType().equals("Bug"))
					&& !(c.getCommand() == CommandValue.RESOLVE && ( (c.getResolution() == Resolution.WONTFIX) || (c.getResolution() == Resolution.DUPLICATE) || (c.getResolution() == Resolution.WORKSFORME)) && Issue.this.getIssueType().equals("Bug")) 
					&& !(c.getCommand() == CommandValue.RESOLVE && ((c.getResolution() == Resolution.WONTFIX) || (c.getResolution() == Resolution.DUPLICATE)) &&  Issue.this.getIssueType().equals("Enhancement"))) {
				throw new UnsupportedOperationException();
			}		
		}
		/**
		 * Name of the state
		 * @return state the state of the isue
		 */
		public String getStateName() {
			return Issue.this.getState();
		}
	}
	/**
	 * Represents possible state for issue
	 * @author josephgrube
	 */
	public class WorkingState implements IssueState {
		/**
		 * Constructor
		 */
			private WorkingState() {
				// Does nothing
			}
			
		/**
		 * Updates state
		 * @param c the command the state is changing to
		 * @throws UnsupportedOperationException if the state operation is illegal
		 */
		public void updateState(Command c) {
			if(c.getResolution() == Resolution.FIXED) {
				Issue.this.setState("Verifying");
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setResolution("Fixed");
			}
			if(getIssueType().equals("Bug") && (c.getResolution() == Resolution.WONTFIX || c.getResolution() == Resolution.DUPLICATE || c.getResolution() == Resolution.WORKSFORME)) {
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				if(c.getResolution() == Resolution.WONTFIX) {
					Issue.this.setResolution("WontFix");
				}
				if(c.getResolution() == Resolution.DUPLICATE) {
					Issue.this.setResolution("Duplicate");
				}
				if(c.getResolution() == Resolution.WORKSFORME) {
					Issue.this.setResolution("WorksForMe");
				}
			}
			if(getIssueType().equals("Enhancement") && (c.getResolution() == Resolution.WONTFIX || c.getResolution() == Resolution.DUPLICATE)) {
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				if(c.getResolution() == Resolution.WONTFIX) {
					Issue.this.setResolution("WontFix");
				}
				if(c.getResolution() == Resolution.DUPLICATE) {
					Issue.this.setResolution("Duplicate");
				}
			}
			if (!(c.getResolution() == Resolution.FIXED) && !(getIssueType().equals("Bug") && (c.getResolution() == Resolution.WONTFIX || c.getResolution() == Resolution.DUPLICATE || c.getResolution() == Resolution.WORKSFORME))
					&& !(getIssueType().equals("Enhancement") && (c.getResolution() == Resolution.WONTFIX || c.getResolution() == Resolution.DUPLICATE))){
				throw new UnsupportedOperationException();
			}
			
		}
		/**
		 * Name of the state
		 * @return state the state of the issue
		 */
		public String getStateName() {
			return Issue.this.getState();
		}
	}
	/**
	 * Represents possible state for issue
	 * @author josephgrube
	 */
	public class ConfirmedState implements IssueState {
		/**
		 * Constructor
		 */
		private ConfirmedState() {
			// Does nothing
		}
		/**
		 * Updates state
		 * @param c the command the state is changing to
		 * @throws UnsupportedOperationException if the state operation is illegal
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.ASSIGN && !(c.getOwnerId() == null || c.getOwnerId().equals(""))) {
				Issue.this.setState("Working");
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setOwner(c.getOwnerId());
			}
			if(c.getCommand() == CommandValue.RESOLVE && c.getResolution() == Resolution.WONTFIX) {
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setResolution("WontFix");
			}
			if(!(c.getCommand() == CommandValue.ASSIGN && !(c.getOwnerId() == null || c.getOwnerId().equals("")))
					&& !(c.getCommand() == CommandValue.RESOLVE && c.getResolution() == Resolution.WONTFIX)) {
				throw new UnsupportedOperationException();
			}
		}
		/**
		 * Name of the state
		 * @return state state of the issue
		 */
		public String getStateName() {
			return Issue.this.getState();
		}
	}
	/**
	 * Represents possible state for issue
	 * @author josephgrube
	 */
	public class VerifyingState implements IssueState {
		/**
		 * Constructor
		 */
		private VerifyingState() {
			// Does nothing
		}
		/**
		 * Updates state
		 * @param c the command the state is changing to
		 * @throws UnsupportedOperationException if the state operation is illegal
		 */
		public void updateState(Command c) {
			if( c.getCommand() == CommandValue.VERIFY) {
				Issue.this.setState("Closed");
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setResolution("Fixed");
			}
			if(c.getCommand() == CommandValue.REOPEN) {
				Issue.this.setState("Working");
				Issue.this.getNotes().add(c.getNote());
				Issue.this.setResolution("");
			}
			if(!(c.getCommand() == CommandValue.VERIFY) && !(c.getCommand() == CommandValue.REOPEN)) {
				throw new UnsupportedOperationException();
			}
		}
		/**
		 * Name of the state
		 * @return state the state of the issue
		 */
		public String getStateName() {
			return Issue.this.getState();
		}
	}
	/**
	 * Represents possible state for issue
	 * @author josephgrube
	 */
	public class ClosedState implements IssueState {
		/**
		 * Constructor
		 */
		private ClosedState() {
			// Does nothing
		}
		/**
		 * Updates state
		 * @param c the command the state is changing to
		 * @throws UnsupportedOperationException if the state operation is illegal
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.REOPEN) {		

				if(Issue.this.getOwner() != null && Issue.this.getIssueType().equals("Enhancement") && !Issue.this.isConfirmed()){
					Issue.this.setState("Working"); 
				}
				else if(Issue.this.getIssueType().equals("Bug") && Issue.this.getOwner() != null && Issue.this.isConfirmed()){
					Issue.this.setState("Working");
				}
				else if(Issue.this.getOwner() == null && Issue.this.isConfirmed()) {
					Issue.this.setState("Confirmed");
				}
				else if(Issue.this.getOwner() == null && !Issue.this.isConfirmed()) {
					Issue.this.setState("New");
				}
				
				Issue.this.setResolution("");
				Issue.this.getNotes().add(c.getNote());
			}
			if(!(c.getCommand() == CommandValue.REOPEN)) {
				throw new UnsupportedOperationException();
			}
		}
		/**
		 * Name of the state
		 * @return state the state of the issue
		 */
		public String getStateName() {
			return Issue.this.getState();
		}
	}


	/**
	 * Interface for states in the Issue State Pattern.  All 
	 * concrete issue states must implement the IssueState interface.
	 * The IssueState interface should be a private interface of the 
	 * Issue class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface IssueState {
		
		/**
		 * Update the Issue based on the given Command.
		 * An UnsupportedOperationException is throw if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Issue's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/**
	 * The possible issue types
	 * @author josephgrube
	 *
	 */
	public enum IssueType {

		/** Issue is an enhancement to the program*/
		ENHANCEMENT ,
		 /**Issue is a bug in the code */
		BUG
		
		
		
	}

}