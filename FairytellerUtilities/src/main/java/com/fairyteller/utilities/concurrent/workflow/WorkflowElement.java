package com.fairyteller.utilities.concurrent.workflow;

public interface WorkflowElement {

	public String getName();
	public UIRunnable getUiRunnable();
	public MainRunnable getMainRunnable();
	public UIHandler getUiHandler();
	public int getMainRunnableTimeout();
	
}
