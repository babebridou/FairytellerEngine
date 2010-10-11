package com.fairyteller.utilities.concurrent.workflow;

public class DefaultBranchingWorkflowElement extends DefaultWorkflowElement implements BranchingWorkflowElement {

	private int nextStateYes;
	private int nextStateNo;
	private int nextStateTimeout;
	private boolean stateFinal;
	private boolean stateFinalSpecified;
	
	public DefaultBranchingWorkflowElement() {
		// TODO Auto-generated constructor stub
	}

	public DefaultBranchingWorkflowElement(int stateIndex, String name,
			UIRunnable startupUiRunnable, MainRunnable mainRunnable,
			UIRunnable finishUiRunnable, int mainRunnableTimeout,
			UIHandler uiHandler, UIRunnable timeoutUiRunnable) {
		super(stateIndex, name, startupUiRunnable, mainRunnable,
				finishUiRunnable, mainRunnableTimeout, uiHandler,
				timeoutUiRunnable);
		
		this.nextStateYes = stateIndex+1;
		this.nextStateNo = stateIndex+1;
		this.nextStateTimeout = -1;
		this.stateFinal = false;
		this.stateFinalSpecified = false;
	}

	public int getNextStateYes() {
		return nextStateYes;
	}

	public void setNextStateYes(int nextStateYes) {
		this.nextStateYes = nextStateYes;
	}

	public int getNextStateNo() {
		return nextStateNo;
	}

	public void setNextStateNo(int nextStateNo) {
		this.nextStateNo = nextStateNo;
	}

	public int getNextStateTimeout() {
		return nextStateTimeout;
	}

	public void setNextStateTimeout(int nextStateTimeout) {
		this.nextStateTimeout = nextStateTimeout;
	}

	public boolean isStateFinal() {
		return stateFinal;
	}

	public void setStateFinal(boolean stateFinal) {
		this.setStateFinalSpecified(true);
		this.stateFinal = stateFinal;
	}
	
	public boolean isStateFinalSpecified() {
		return stateFinalSpecified;
	}
	
	protected void setStateFinalSpecified(boolean isStateFinalSpecified) {
		this.stateFinalSpecified = isStateFinalSpecified;
	}
	

}
