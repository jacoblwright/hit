package model;

import java.util.Collection;


/**
 * This class holds the data structures that manage the undo/redo functionality.
 * @author Nick
 *
 */
public class CommandHistory {
	
	Collection<Command> undoStack;
	
	Collection<Command> redoStack;
	
	/**
	 * Builds a command history class
	 */
	public CommandHistory(){
	
	}
	
	
	/**
	 * Executes the command and pushes it on the undo stack
	 */
	public void doCommand(Command doCommand){
		
	}
	
	/** 
	 * Reports whether or not an undo operation can be performed.
	 * 
	 * @return true if undo stack is not empty, false otherwise
	 */
	public boolean canUndo(){
		return false;
	}
	
	/**
	 * Pops the topmost command from the undo stack and calls it's undo method.
	 * Then adds the command to the redo stack.
	 */
	public void undo(){
		
	}
	
	/**
	 * Reports whether or not the redo operation can be performed.
	 * 
	 * @return true if redo stack is not empty, false otherwise.
	 */
	public boolean canRedo(){
		return false;
	}
	
	/**
	 * Pops the topmost command from the redo stack, calls it's redo method,
	 * and then adds the command back onto the undo stack.
	 */
	public void redo(){
		
	}
	

}
