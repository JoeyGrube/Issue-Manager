package edu.ncsu.csc216.issue_manager.model.command;

/**
 * This class sends commands to Issue to change an issues state
 * @author josephgrube
 *
 */
public class Command {

	/** Possible resolution status*/
	public static final String R_FIXED = "Fixed";
	/** Possible resolution status*/
	public static final String R_DUPLICATE = "Duplicate";
	/** Possible resolution status*/
	public static final String R_WONTFIX = "WontFix";
	/** Possible resolution status*/
	public static final String R_WORKSFORME = "WorksForMe";
	/** Who's in charge of this issue */
	private String ownerId;
	/** Additional info about the issue*/
	private String note;
	/** The command value*/
	private CommandValue c;
	/** The resolution if the command value is resolved*/
	private Resolution res;
	
	/**
	 * Constructor
	 * @param value the value of the issue
	 * @param ownerId the person working on the issue
	 * @param res the resolution of the issue
	 * @param note additional info about the issue
	 * @throws IllegalArgumentException if there is an invalid parameter
	 * or an invalid combination of parameters
	 */
	public Command(CommandValue value, String ownerId, Resolution res, String note){
		if(value == null) {
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.ASSIGN && (ownerId == null || ownerId.equals(""))) {
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.RESOLVE && res == null) {
			throw new IllegalArgumentException();
		}
		if(note == null || note.equals("")) {
			throw new IllegalArgumentException();
		}
		this.ownerId = ownerId;
		this.note = note;
		this.c = value;
		this.res = res;
		
	}
	
	/**
	 * Gets command
	 * @return c the command value
	 */
	public CommandValue getCommand() {
		return c;
	}
	
	/**
	 * gets owner id
	 * @return ownerId the person tasked with fixing the issue
	 */
	public String getOwnerId() {
		return ownerId;
	}
	
	/**
	 * gets resolution
	 * @return res the resolution of the command
	 */
	public Resolution getResolution() {
		return res;
	}
	
	/**
	 * Gets note
	 * @return note additional info on the issue
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Command values
	 * @author josephgrube
	 *
	 */
	public enum CommandValue {
		/** Assigns the issue to someone*/
		ASSIGN, 
		/** Confirms the issue */
		CONFIRM,
		/** Resolve the issue*/
		RESOLVE,
		/** Verify it's resolved*/
		VERIFY, 
		/** Reopens the issue if not resolved*/
		REOPEN	
	}
	
	/**
	 * Resolutions
	 * @author josephgrube
	 *
	 */
	public enum Resolution {
		/** Issue is fixed*/
		FIXED, 
		/** Already on the list*/
		DUPLICATE,
		/** Not worth it*/
		WONTFIX, 
		/** Not an issue*/
		WORKSFORME
	}
}
