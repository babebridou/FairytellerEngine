package com.fairyteller.utilities.concurrent.workflow;

/**
 * The simplest workflow I could think of It starts by calling a UI update to
 * tell the user that the workflow has started Then it performs the main
 * runnable with a timeout Once it's done it calls a UI update to tell the user
 * that the workflow is done processing.
 * 
 * @author tphilipakis
 * 
 */
public class SimpleWorkflow implements Workflow {

	private UIRunnable startupUIRunnable;
	private MainRunnable mainRunnable;
	private int timeoutInMilliseconds;
	private UIRunnable finishUIRunnable;
	private final UIHandler uiHandler;

	public SimpleWorkflow(UIRunnable startupUIRunnable,
			MainRunnable mainRunnable, int timeoutInMilliseconds,
			UIRunnable finishUIRunnable, final UIHandler uiHandler) {
		this.startupUIRunnable = startupUIRunnable;
		this.mainRunnable = mainRunnable;
		this.finishUIRunnable = finishUIRunnable;
		this.timeoutInMilliseconds = timeoutInMilliseconds;
		this.uiHandler = uiHandler;
	}

	@Override
	public MainRunnable getMainRunnable(int stateIndex) {
			return mainRunnable;
	}

	@Override
	public UIRunnable getStartupUIRunnable(int stateIndex) {
		return startupUIRunnable;
	}

	@Override
	public UIRunnable getFinishUIRunnable(int stateIndex) {
		return finishUIRunnable;
	}
	
	@Override
	public int getNextState(int stateIndex) {
		return 0;
	}

	@Override
	public int getStartState() {
		return 0;
	}

	@Override
	public boolean isStateFinal(int stateIndex) {
		return true;
	}

	@Override
	public int getMainTimeout(int stateIndex) {
			return timeoutInMilliseconds;
	}
	
	@Override
	public UIHandler getUIHandler(int stateIndex) {
		return uiHandler;
	}
	
	@Override
	public int getOnTimeoutNextState(int stateIndex) {
		return -1;
	}
	
	@Override
	public UIRunnable getTimeoutUIRunnable(int stateIndex) {
		return null;
	}
}
