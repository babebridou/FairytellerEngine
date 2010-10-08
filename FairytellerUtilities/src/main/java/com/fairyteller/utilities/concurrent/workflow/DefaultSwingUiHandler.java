package com.fairyteller.utilities.concurrent.workflow;

import javax.swing.SwingUtilities;

public class DefaultSwingUiHandler implements UIHandler{
	@Override
	public void post(UIRunnable uiRunnable) {
		SwingUtilities.invokeLater(uiRunnable);
	}
	
}
