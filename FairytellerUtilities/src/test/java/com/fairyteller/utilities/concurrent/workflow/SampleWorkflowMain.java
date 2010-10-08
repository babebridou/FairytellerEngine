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
				myComponent.add(new JLabel("The Workflow Test has now finished!"));
				myComponent.revalidate();
			}
		};

		final UIHandler handler = new DefaultSwingUiHandler();
		final SimpleWorkflow workflow = new SimpleWorkflow(startUiRunnable,
				mainRunnable, 2000, endUIRunnable, handler);
		final SimpleWorkflowService service = new SimpleWorkflowService();
		
		Action processWorkflowAction = new AbstractAction("Process the workflow"){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				this.setEnabled(false);
				new SwingWorker<String, Object>(){
					@Override
					protected String doInBackground() throws Exception {
						service.start(workflow);
						return "done";
					}
					@Override
					protected void done() {
						try {
							String res = get();
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
