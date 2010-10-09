package com.fairyteller.utilities.concurrent.workflow;

public interface Workflow {

	public UIHandler getUIHandler(int stateIndex);
	public UIRunnable getStartupUIRunnable(int stateIndex);
	public UIRunnable getFinishUIRunnable(int stateIndex);
	public UIRunnable getTimeoutUIRunnable(int stateIndex);
	public MainRunnable getMainRunnable(int stateIndex);
	public int getNextState(int stateIndex);
	public boolean isStateFinal(int stateIndex);
	public int getStartState();
	public int getMainTimeout(int stateIndex);
	public int getOnTimeoutNextState(int stateIndex);
}
