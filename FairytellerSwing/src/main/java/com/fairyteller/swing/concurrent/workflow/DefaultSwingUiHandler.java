package com.fairyteller.swing.concurrent.workflow;

import javax.swing.SwingUtilities;

import com.fairyteller.utilities.concurrent.workflow.UIHandler;
import com.fairyteller.utilities.concurrent.workflow.UIRunnable;

public class DefaultSwingUiHandler implements UIHandler{
	@Override
	public void post(UIRunnable uiRunnable) {
		SwingUtilities.invokeLater(uiRunnable);
	}
	
}
