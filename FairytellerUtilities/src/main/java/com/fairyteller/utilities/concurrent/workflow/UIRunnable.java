package com.fairyteller.utilities.concurrent.workflow;

public interface UIRunnable extends Runnable{
	public boolean isSuccess();
	public void setSuccess(boolean success);
	public Object[] getArguments();
	public void setArguments(Object[] arguments);

	
	/**
	 * must be implemented
	 * if the runnable is an action, use setSuccess for the next runnable to be informed
	 */
	@Override
	public abstract void run();
}
