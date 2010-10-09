package com.fairyteller.utilities.concurrent.workflow;

public interface WorkflowElement {

	public String getName();
	public UIRunnable getStartupUiRunnable();
	public UIRunnable getFinishUiRunnable();
	public MainRunnable getMainRunnable();
	public UIRunnable getTimeoutUiRunnable();
	public UIHandler getUiHandler();
	public int getMainRunnableTimeout();
	
}
