package com.fairyteller.utilities.concurrent.workflow;

public interface MainRunnable extends Runnable{

	public boolean isSuccess();
	public void setSuccess(boolean success);
	
	public boolean isProceed();
	public void setProceed(boolean proceed);
	
	
	/**
	 * must be implemented
	 * if the runnable is an action, use setSuccess for the next runnable to be informed
	 */
	@Override
	public abstract void run();

	public Object[] getArguments();
	public void setArguments(Object[] arguments);
}
