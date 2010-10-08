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

	public static final int START = 0;
	public static final int END = 1;
	public static final int[] NEXT_ARRAY = { 1, 1 };
	private UIRunnable startUIRunnable;
	private MainRunnable mainRunnable;
	private int timeoutInMilliseconds;
	private UIRunnable endUIRunnable;
	private final UIHandler uiHandler;

	public SimpleWorkflow(UIRunnable startUIRunnable,
			MainRunnable mainRunnable, int timeoutInMilliseconds,
			UIRunnable endUIRunnable, final UIHandler uiHandler) {
		this.startUIRunnable = startUIRunnable;
		this.mainRunnable = mainRunnable;
		this.endUIRunnable = endUIRunnable;
		this.timeoutInMilliseconds = timeoutInMilliseconds;
		this.uiHandler = uiHandler;
	}

	@Override
	public MainRunnable getMainRunnable(int stateIndex) {
		switch (stateIndex) {
		case START:
			return null;
		case END:
			return mainRunnable;
		default:
			return null;
		}
	}

	@Override
	public UIRunnable getUIRunnable(int stateIndex) {
		switch (stateIndex) {
		case START:
			return startUIRunnable;
		case END:
			return endUIRunnable;
		default:
			return null;
		}
	}

	@Override
	public int getNextState(int stateIndex) {
		int next = NEXT_ARRAY[stateIndex];
		return next;
	}

	@Override
	public int getStartState() {
		return 0;
	}

	@Override
	public boolean isStateFinal(int stateIndex) {
		return NEXT_ARRAY[stateIndex] == stateIndex;
	}

	@Override
	public int getMainTimeout(int stateIndex) {
		if (stateIndex == END)
			return timeoutInMilliseconds;
		return -1;
	}
	
	@Override
	public UIHandler getUIHandler(int stateIndex) {
		return uiHandler;
	}
}
