package com.fairyteller.utilities.concurrent.workflow;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SimpleWorkflowService {
	private ScheduledExecutorService service;
	
	public void start(Workflow workflow){
		initWorkflowState(workflow.getStartState(), workflow);
	}
	
	private void initWorkflowState(int stateIndex, Workflow workflow){
		if(service!=null && !(service.isShutdown()||service.isTerminated())){
			service.shutdownNow();
		}
		service = Executors.newScheduledThreadPool(2);
		Future<Boolean> taskFuture = service.submit(new MyWorkflowCallable(stateIndex, workflow));
		service.schedule(new Timeout(taskFuture), workflow.getMainTimeout(stateIndex), TimeUnit.MILLISECONDS);
		service.shutdown();
		try {
			if(taskFuture.get()){
				if(!workflow.isStateFinal(stateIndex)){
					initWorkflowState(workflow.getNextState(stateIndex), workflow);
				}
			}
		} catch (CancellationException e) {
			UIRunnable timeoutUiRunnable = workflow.getTimeoutUIRunnable(stateIndex);
			if(timeoutUiRunnable!=null){
				workflow.getUIHandler(stateIndex).post(timeoutUiRunnable);
			}
			int onTimeoutNextState = workflow.getOnTimeoutNextState(stateIndex);
			if(onTimeoutNextState >=0){
				initWorkflowState(onTimeoutNextState, workflow);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private class MyWorkflowCallable implements Callable<Boolean>{
		private int state;
		private Workflow workflow;
		
		public MyWorkflowCallable(int state, Workflow workflow) {
			this.state = state;
			this.workflow = workflow;
		}

		@Override
		public Boolean call() throws Exception {
			processWorkflow(this.state, workflow);
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
	
	private void processWorkflow(int stateIndex, Workflow workflow){
		boolean success = false;
		UIRunnable startupUiRunnable = workflow.getStartupUIRunnable(stateIndex);
		if(startupUiRunnable!=null){
			workflow.getUIHandler(stateIndex).post(startupUiRunnable);
		}
		MainRunnable mainRunnable = workflow.getMainRunnable(stateIndex);
		if(mainRunnable!=null){
			mainRunnable.run();
			success = mainRunnable.isSuccess();
		}
		
		UIRunnable finishUiRunnable = workflow.getFinishUIRunnable(stateIndex);
		if(finishUiRunnable!=null  && mainRunnable.isProceed()){
			finishUiRunnable.setSuccess(success);
			workflow.getUIHandler(stateIndex).post(finishUiRunnable);
		}
	}
}
