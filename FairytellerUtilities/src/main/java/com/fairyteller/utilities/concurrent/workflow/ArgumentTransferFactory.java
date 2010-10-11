package com.fairyteller.utilities.concurrent.workflow;

public interface ArgumentTransferFactory {

	public Object[] transferFromMainToUi(Object[] arguments);
	public Object[] transferFromUiToMain(Object[] arguments);
	
}
