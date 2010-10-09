package com.fairyteller.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.fairyteller.swing.concurrent.workflow.DefaultSwingUiHandler;
import com.fairyteller.utilities.concurrent.workflow.AbstractWorkflowRunnable;
import com.fairyteller.utilities.concurrent.workflow.DefaultWorkflowElement;
import com.fairyteller.utilities.concurrent.workflow.MainRunnable;
import com.fairyteller.utilities.concurrent.workflow.SimpleWorkflowService;
import com.fairyteller.utilities.concurrent.workflow.UIHandler;
import com.fairyteller.utilities.concurrent.workflow.UIRunnable;
import com.fairyteller.utilities.concurrent.workflow.Workflow;
import com.fairyteller.utilities.concurrent.workflow.WorkflowElement;
import com.fairyteller.utilities.concurrent.workflow.WorkflowFactory;

public class SampleWorkflowMain {

	public static void main(String[] args) {
		final JPanel myComponent = new JPanel(new FlowLayout());

		FrameHelper.createMainFrame("test Workflow", myComponent, true);

		UIRunnable firstStartUiRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				myComponent.add(new JLabel("First task started..."));
				myComponent.revalidate();
			}
		};

		MainRunnable firstMainRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				  try {
				 
					setProceed(true);
					Thread.sleep(1000);
					setSuccess(true);
				} catch (InterruptedException e) {
					setProceed(false);
				}
			}
		};

		UIRunnable firstFinishUiRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				myComponent
						.add(new JLabel("First task completed!"));
				myComponent.revalidate();
			}
		};

		UIRunnable secondStartUiRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				myComponent
						.add(new JLabel("Second task started..."));
				myComponent.revalidate();
			}
		};
		
		MainRunnable secondMainRunnable = new AbstractWorkflowRunnable() {
			@Override
			public void run() {
				try {
					setProceed(true);
					Thread.sleep(1000);
					setSuccess(true);
				} catch (InterruptedException e) {
					setProceed(false);
				}
				
			}
		};

		UIRunnable secondFinishUIRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				if(isSuccess()){
				myComponent.add(new JLabel(
						"Second task completed!"));
				} else {
					myComponent.add(new JLabel(
					"Second task failed!"));
				}
				myComponent.revalidate();
			}
		};

		UIRunnable timeoutUiRunnable = new AbstractWorkflowRunnable() {

			@Override
			public void run() {
				myComponent
						.add(new JLabel("Timeout :("));
				myComponent.revalidate();
			}
		};
		
		final UIHandler handler = new DefaultSwingUiHandler();

		WorkflowElement[] welements = new WorkflowElement[] {
				new DefaultWorkflowElement(0, "first", firstStartUiRunnable, firstMainRunnable, firstFinishUiRunnable,
						3000, handler, timeoutUiRunnable),
				new DefaultWorkflowElement(0, "second", secondStartUiRunnable,
						secondMainRunnable, secondFinishUIRunnable, 300, handler, timeoutUiRunnable)};

		final Workflow workflow = WorkflowFactory
				.createStraightWorkflow(welements);

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
							JOptionPane.showMessageDialog(myComponent, "problème!");
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
