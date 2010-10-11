package com.fairyteller.utilities.concurrent.workflow;

public interface BranchingWorkflowElement extends WorkflowElement{
	public int getNextStateYes();

	public int getNextStateNo();

	public int getNextStateTimeout();

	public boolean isStateFinal();
	
	public boolean isStateFinalSpecified();
	
}
