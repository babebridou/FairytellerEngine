package com.fairyteller.utilities.concurrent.workflow;

public abstract class AbstractWorkflowRunnable implements UIRunnable, MainRunnable{
	private boolean success = true;
	private boolean proceed = true;
	
	public void setSuccess(boolean success){
		this.success = success;
	}
	
	public boolean isSuccess(){
		return this.success;
	}
	
	public void setProceed(boolean proceed){
		this.proceed = proceed;
	}
	public boolean isProceed(){
		return this.proceed;
	}
	/**
	 * must be implemented
	 * if the runnable is an action, use setSuccess for the next runnable to be informed
	 */
	@Override
	public abstract void run();
}
