package model;

import java.io.IOException;
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
		try {
			Model.getInstance().getTransaction().startTransaction();
			for (ICommand cmd : commandList) {
				cmd.execute();
			}
			Model.getInstance().getTransaction().endTransaction();
		} catch (IOException e) {
			System.out.print("An error occured while starting database transation");
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		try {
			Model.getInstance().getTransaction().startTransaction();
		
			for (int i = (commandList.size() - 1); i >= 0; i-- ) {
				commandList.get(i).unexecute();
			}
			Model.getInstance().getTransaction().endTransaction();
		} catch (IOException e) {
			System.out.print("An error occured while starting database transation");
			e.printStackTrace();
		}
	}
	
	

}
