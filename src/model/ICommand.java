package model;

/** Represents an autonomous command as well as it's inverse.
 * 
 * @author Nick
 *
 */
public interface ICommand {
	
	/** 
	 * Iterates forward through the command list executing each sub command.
	 */
	public void execute();
	
	/**
	 * Iterates backward through the command list executing each sub command.
	 */
	public void unexecute();
}
