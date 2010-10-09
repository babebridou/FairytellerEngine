package com.fairyteller.utilities.concurrent.workflow;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.fairyteller.swing.FrameHelper;

public class SampleWorkflowMain {

	public static void main(String[] args) {
		final JPanel myComponent = new JPanel(new FlowLayout());

		FrameHelper.createMainFrame("test Workflow", myComponent, true);

		UIRunnable startUiRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				myComponent.add(new JLabel("The workflow test just started!"));
				myComponent.revalidate();
			}
		};

		MainRunnable debutRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		UIRunnable debutUiRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				myComponent
						.add(new JLabel("The Workflow Test is now running!"));
				myComponent.revalidate();
			}
		};

		MainRunnable mainRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		UIRunnable endUIRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				myComponent.add(new JLabel(
						"The Workflow Test has now finished!"));
				myComponent.revalidate();
			}
		};

		final UIHandler handler = new DefaultSwingUiHandler();

		WorkflowElement[] welements = new WorkflowElement[] {
				new DefaultWorkflowElement(0, "start", startUiRunnable, null,
						10, handler),
				new DefaultWorkflowElement(0, "debut", debutUiRunnable,
						debutRunnable, 3000, handler),
				new DefaultWorkflowElement(0, "end", endUIRunnable,
						mainRunnable, 3000, handler) };

		final Workflow workflow = WorkflowFactory
				.createStraightWorkflow(welements);

		// new SimpleWorkflow(startUiRunnable,
		// mainRunnable, 2000, endUIRunnable, handler);
		final SimpleWorkflowService service = new SimpleWorkflowService();

		Action processWorkflowAction = new AbstractAction(
				"Process the workflow") {

					private static final long serialVersionUID = 3949757251884123525L;

			@Override
			public void actionPerformed(ActionEvent e) {
				this.setEnabled(false);
				new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() throws Exception {
						service.start(workflow);
						return "done";
					}

					@Override
					protected void done() {
						try {
							get();
							setEnabled(true);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}.execute();
			}
		};

		myComponent.add(new JButton(processWorkflowAction));

		myComponent.revalidate();
	}
}
