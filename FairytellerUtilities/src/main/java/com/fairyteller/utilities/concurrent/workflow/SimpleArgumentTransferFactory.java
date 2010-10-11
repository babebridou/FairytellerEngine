package com.fairyteller.utilities.concurrent.workflow;

public class SimpleArgumentTransferFactory implements ArgumentTransferFactory {
	@Override
	public Object[] transferFromMainToUi(Object[] arguments) {
		return arguments;
	}

	@Override
	public Object[] transferFromUiToMain(Object[] arguments) {
		return arguments;
	}
}
