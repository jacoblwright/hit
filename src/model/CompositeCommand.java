package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class CompositeCommand implements ICommand {

	List<ICommand> commandList;
	
	public CompositeCommand(){
		commandList = new ArrayList<ICommand>();
	}
	
	@Override
	public void execute() {
		for (ICommand cmd : commandList) {
			cmd.execute();
		}
	}

	@Override
	public void unexecute() {
		for (int i = (commandList.size() - 1); i >= 0; i-- ) {
			commandList.get(i).unexecute();
		}
		
	}
	
	

}
