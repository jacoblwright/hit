package model;

import java.util.Stack;


/**
 * This class holds the data structures that manage the undo/redo functionality.
 * @author Nick
 *
 */
public class CommandHistory {
	
	Stack<ICommand> undoStack;
	
	Stack<ICommand> redoStack;
	
	/**
	 * Builds a command history class
	 */
	public CommandHistory(){
		undoStack = new Stack<ICommand>();
		redoStack = new Stack<ICommand>();
	
	}
	
	
	/**
	 * Executes the command and pushes it on the undo stack
	 */
	public void doCommand(ICommand doCommand){
		undoStack.push(doCommand);
		doCommand.execute();
	}
	
	/** 
	 * Reports whether or not an undo operation can be performed.
	 * 
	 * @return true if undo stack is not empty, false otherwise
	 */
	public boolean canUndo(){
		return !undoStack.isEmpty();
	}
	
	/**
	 * Pops the topmost command from the undo stack and calls it's undo method.
	 * Then adds the command to the redo stack.
	 */
	public void undo(){
		ICommand cmd = undoStack.pop();
		cmd.unexecute();
		redoStack.push(cmd);
	}
	
	/**
	 * Reports whether or not the redo operation can be performed.
	 * 
	 * @return true if redo stack is not empty, false otherwise.
	 */
	public boolean canRedo(){
		return !redoStack.isEmpty();
	}
	
	/**
	 * Pops the topmost command from the redo stack, calls it's redo method,
	 * and then adds the command back onto the undo stack.
	 */
	public void redo(){
		ICommand cmd = redoStack.pop();
		cmd.execute();
		undoStack.push(cmd);
		
	}
	

}
