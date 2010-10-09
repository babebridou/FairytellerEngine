package com.fairyteller.utilities.concurrent.workflow;

public class WorkflowFactory {

	
	public static Workflow createStraightWorkflow(final WorkflowElement[] elements){
		Workflow w = new StraightWorkflow(elements);
		return w;
	}
	
	private static final class StraightWorkflow implements Workflow{
		private WorkflowElement[] elements;
		
		public StraightWorkflow(WorkflowElement[] elements) {
			this.elements = elements;
		}

		@Override
		public UIHandler getUIHandler(int stateIndex) {
			return elements[stateIndex].getUiHandler();
		}

		@Override
		public UIRunnable getUIRunnable(int stateIndex) {
			return elements[stateIndex].getUiRunnable();
		}

		@Override
		public MainRunnable getMainRunnable(int stateIndex) {
			return elements[stateIndex].getMainRunnable();
		}

		@Override
		public int getNextState(int stateIndex) {
			return stateIndex+1;
		}

		@Override
		public boolean isStateFinal(int stateIndex) {
			return stateIndex == elements.length-1;
		}

		@Override
		public int getStartState() {
			return 0;
		}

		@Override
		public int getMainTimeout(int stateIndex) {
			return elements[stateIndex].getMainRunnableTimeout();
		}
		
	}
}
