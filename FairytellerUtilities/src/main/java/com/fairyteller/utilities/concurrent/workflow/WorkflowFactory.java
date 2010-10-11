package com.fairyteller.utilities.concurrent.workflow;

public class WorkflowFactory {
	public static final int STATE_IS_FINAL = 1;
	public static final int STATE_IS_NOT_FINAL = 0;

	public static Workflow createStraightWorkflow(
			final WorkflowElement[] elements) {
		Workflow w = new StraightWorkflow(elements);
		return w;
	}

	public static Workflow createBranchingWorkflow(
			final WorkflowElement[] elements, int[] transitionsYes,
			int[] transitionsNo, int[] transitionsTimeout, int[] finishStates) {
		Workflow w = new BranchingWorkflow(elements, transitionsYes,
				transitionsNo, transitionsTimeout, finishStates);
		return w;
	}

	public static Workflow createBranchingWorkflow(
			BranchingWorkflowElement[] elements) {
		int size = elements.length;
		int[] transitionsYes = new int[size];
		int[] transitionsNo = new int[size];
		int[] transitionsTimeout = new int[size];
		int[] finishStates = new int[size];
		boolean wasFinalStateEverSpecified = false;

		for (int i = 0; i < elements.length; i++) {
			transitionsYes[i] = elements[i].getNextStateYes();
			transitionsNo[i] = elements[i].getNextStateNo();
			transitionsTimeout[i] = elements[i].getNextStateTimeout();
			finishStates[i] = elements[i].isStateFinal() ? STATE_IS_FINAL
					: STATE_IS_NOT_FINAL;
			if (elements[i].isStateFinalSpecified()) {
				wasFinalStateEverSpecified = true;
			}
		}
		if (!wasFinalStateEverSpecified) {
			// if no final state was ever specified as true or false in any of
			// the transitions then the last state is final
			finishStates[size - 1] = STATE_IS_FINAL;
		}

		return createBranchingWorkflow(elements, transitionsYes, transitionsNo,
				transitionsTimeout, finishStates);
	}

	private static final class StraightWorkflow implements Workflow {
		private WorkflowElement[] elements;

		public StraightWorkflow(WorkflowElement[] elements) {
			this.elements = elements;
		}

		@Override
		public UIHandler getUIHandler(int stateIndex) {
			return elements[stateIndex].getUiHandler();
		}

		@Override
		public UIRunnable getStartupUIRunnable(int stateIndex) {
			return elements[stateIndex].getStartupUiRunnable();
		}

		@Override
		public UIRunnable getFinishUIRunnable(int stateIndex) {
			return elements[stateIndex].getFinishUiRunnable();
		}

		@Override
		public MainRunnable getMainRunnable(int stateIndex) {
			return elements[stateIndex].getMainRunnable();
		}

		@Override
		public int getNextState(int stateIndex) {
			return stateIndex + 1;
		}

		@Override
		public boolean isStateFinal(int stateIndex) {
			return stateIndex == elements.length - 1;
		}

		@Override
		public int getStartState() {
			return 0;
		}

		@Override
		public int getMainTimeout(int stateIndex) {
			return elements[stateIndex].getMainRunnableTimeout();
		}

		@Override
		public int getOnTimeoutNextState(int stateIndex) {
			return -1;
		}

		@Override
		public UIRunnable getTimeoutUIRunnable(int stateIndex) {
			return elements[stateIndex].getTimeoutUiRunnable();
		}

	}

	private static final class BranchingWorkflow implements Workflow {
		private WorkflowElement[] elements;
		private int[] transitionsYes;
		private int[] transitionsNo;
		private int[] transitionsTimeout;
		private int[] finishStates;

		public BranchingWorkflow(WorkflowElement[] elements,
				int[] transitionsYes, int[] transitionsNo,
				int[] transitionsTimeout, int[] finishStates) {
			this.elements = elements;
			this.transitionsYes = transitionsYes;
			this.transitionsNo = transitionsNo;
			this.transitionsTimeout = transitionsTimeout;
			this.finishStates = finishStates;
		}

		@Override
		public UIHandler getUIHandler(int stateIndex) {
			return elements[stateIndex].getUiHandler();
		}

		@Override
		public UIRunnable getStartupUIRunnable(int stateIndex) {
			return elements[stateIndex].getStartupUiRunnable();
		}

		@Override
		public UIRunnable getFinishUIRunnable(int stateIndex) {
			return elements[stateIndex].getFinishUiRunnable();
		}

		@Override
		public MainRunnable getMainRunnable(int stateIndex) {
			return elements[stateIndex].getMainRunnable();
		}

		@Override
		public int getStartState() {
			return 0;
		}

		@Override
		public int getMainTimeout(int stateIndex) {
			return elements[stateIndex].getMainRunnableTimeout();
		}

		@Override
		public int getOnTimeoutNextState(int stateIndex) {
			return transitionsTimeout[stateIndex];
		}

		@Override
		public UIRunnable getTimeoutUIRunnable(int stateIndex) {
			return elements[stateIndex].getTimeoutUiRunnable();
		}

		@Override
		public int getNextState(int stateIndex) {
			if (elements[stateIndex].getMainRunnable().isSuccess())
				return transitionsYes[stateIndex];
			else
				return transitionsNo[stateIndex];
		}

		@Override
		public boolean isStateFinal(int stateIndex) {
			return finishStates[stateIndex] == 1;
		}
	}
}
