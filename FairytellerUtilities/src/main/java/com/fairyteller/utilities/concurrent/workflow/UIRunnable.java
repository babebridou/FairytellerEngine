package com.fairyteller.utilities.concurrent.workflow;

public interface UIRunnable extends Runnable{
	public boolean isSuccess();
	public void setSuccess(boolean success);

	
	/**
	 * must be implemented
	 * if the runnable is an action, use setSuccess for the next runnable to be informed
	 */
	@Override
	public abstract void run();
}
