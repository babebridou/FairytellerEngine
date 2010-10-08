package com.fairyteller.utilities.concurrent.workflow;

public abstract class AbstractWorkflowRunnable implements UIRunnable, MainRunnable{
	private boolean success = true;
	
	public void setSuccess(boolean success){
		this.success = success;
	}
	
	public boolean isSuccess(){
		return this.success;
	}
	
	
	/**
	 * must be implemented
	 * if the runnable is an action, use setSuccess for the next runnable to be informed
	 */
	@Override
	public abstract void run();
}
