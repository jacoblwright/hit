package model;

/** Represents an autonomous command as well as it's inverse.
 * 
 * @author Nick
 *
 */
public abstract class Command {
	
	/**
	 * Builds the command and sub commands (if needed).
	 */
	public Command() {
	}
	
	/** 
	 * Iterates forward through the command list executing each sub command.
	 */
	public void execute(){}
	
	/**
	 * Iterates backward through the command list executing each sub command.
	 */
	public void unexecute(){}
}
