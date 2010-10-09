package com.fairyteller.utilities.concurrent.workflow;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Deprecated
public class WorkflowService {

	private Workflow workflow;
	private List<UIRunnable> uiRunnables;
	private List<MainRunnable> mainRunnables;
	private ScheduledExecutorService globalScheduledExecutorService;
	
	public void processWorkflow(int startStateIndex){
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		Future<String> taskFuture = service.submit(new WorkflowCallable(startStateIndex));
		service.schedule(new Timeout(taskFuture), 8000, TimeUnit.MILLISECONDS);
		service.shutdown();
		try {
			taskFuture.get();
		} catch (CancellationException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private final class WorkflowCallable implements Callable<String> {

		private int state;
		
		public WorkflowCallable(int state) {

			this.state = state;
		}

		@Override
		public String call() throws Exception {
			processWorkflow(this.state);
			return "done";
		}
	}

	class Timeout implements Runnable {
		public Timeout(Future<?> target) {
			this.target = target;
		}

		@Override
		public void run() {
			target.cancel(true);
		}

		private Future<?> target;
	}
}
