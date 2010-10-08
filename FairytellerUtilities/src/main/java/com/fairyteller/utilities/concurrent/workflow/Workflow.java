package com.fairyteller.utilities.concurrent.workflow;

public interface Workflow {

	public UIHandler getUIHandler(int stateIndex);
	public UIRunnable getUIRunnable(int stateIndex);
	public MainRunnable getMainRunnable(int stateIndex);
	public int getNextState(int stateIndex);
	public boolean isStateFinal(int stateIndex);
	public int getStartState();
	public int getMainTimeout(int stateIndex);
	
}
