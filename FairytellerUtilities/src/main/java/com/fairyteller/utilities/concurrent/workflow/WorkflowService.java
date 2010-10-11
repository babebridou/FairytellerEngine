package com.fairyteller.utilities.concurrent.workflow;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkflowService {
	private ScheduledExecutorService service;
	private ArgumentTransferFactory transferFactory;

	public WorkflowService(ArgumentTransferFactory argumentTransferFactory) {
		this.transferFactory = argumentTransferFactory;
	}

	public WorkflowService() {
		this.transferFactory = new SimpleArgumentTransferFactory();
	}

	public ArgumentTransferFactory getTransferFactory() {
		return transferFactory;
	}

	public void setTransferFactory(ArgumentTransferFactory transferFactory) {
		this.transferFactory = transferFactory;
	}

	public void start(Workflow workflow, Object[] arguments) {
		initWorkflowState(workflow.getStartState(), workflow,
				transferFactory.transferFromUiToMain(arguments));
	}

	private void initWorkflowState(int stateIndex, Workflow workflow,
			Object[] arguments) {
		if (service != null
				&& !(service.isShutdown() || service.isTerminated())) {
			service.shutdownNow();
		}
		service = Executors.newScheduledThreadPool(2);
		Future<Boolean> taskFuture = service.submit(new MyWorkflowCallable(
				stateIndex, workflow, arguments));
		service.schedule(new Timeout(taskFuture),
				workflow.getMainTimeout(stateIndex), TimeUnit.MILLISECONDS);
		service.shutdown();
		try {
			if (taskFuture.get()) {
				if (!workflow.isStateFinal(stateIndex)) {
					initWorkflowState(workflow.getNextState(stateIndex),
							workflow, workflow.getMainRunnable(stateIndex)
									.getArguments());
				}
			}
		} catch (CancellationException e) {
			UIRunnable timeoutUiRunnable = workflow
					.getTimeoutUIRunnable(stateIndex);
			if (timeoutUiRunnable != null) {
				workflow.getUIHandler(stateIndex).post(timeoutUiRunnable);
			}
			int onTimeoutNextState = workflow.getOnTimeoutNextState(stateIndex);
			if (onTimeoutNextState >= 0) {
				initWorkflowState(onTimeoutNextState, workflow, workflow
						.getMainRunnable(stateIndex).getArguments());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private class MyWorkflowCallable implements Callable<Boolean> {
		private int state;
		private Workflow workflow;
		private Object[] arguments;

		public MyWorkflowCallable(int state, Workflow workflow,
				Object[] arguments) {
			this.state = state;
			this.workflow = workflow;
			this.arguments = arguments;
		}

		@Override
		public Boolean call() throws Exception {
			processWorkflow(this.state, workflow, arguments);
			return true;
		}
	}

	private class Timeout implements Runnable {
		public Timeout(Future<?> target) {
			this.target = target;
		}

		@Override
		public void run() {
			target.cancel(true);
		}

		private Future<?> target;
	}

	private void processWorkflow(int stateIndex, Workflow workflow,
			Object[] arguments) {
		boolean success = false;
		UIRunnable startupUiRunnable = workflow
				.getStartupUIRunnable(stateIndex);
		if (startupUiRunnable != null) {
			startupUiRunnable.setArguments(transferFactory
					.transferFromMainToUi(arguments));
			workflow.getUIHandler(stateIndex).post(startupUiRunnable);
		}
		MainRunnable mainRunnable = workflow.getMainRunnable(stateIndex);
		Object[] newArguments = null;
		if (mainRunnable != null) {
			mainRunnable.setArguments(arguments);
			mainRunnable.run();
			success = mainRunnable.isSuccess();
			newArguments = mainRunnable.getArguments();
		}

		UIRunnable finishUiRunnable = workflow.getFinishUIRunnable(stateIndex);
		if (finishUiRunnable != null && mainRunnable.isProceed()) {
			finishUiRunnable.setArguments(transferFactory
					.transferFromMainToUi(newArguments));
			finishUiRunnable.setSuccess(success);
			workflow.getUIHandler(stateIndex).post(finishUiRunnable);
		}
	}
}
