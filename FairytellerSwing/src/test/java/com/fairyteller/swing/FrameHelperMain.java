package com.fairyteller.swing;

import javax.swing.JLabel;

public class FrameHelperMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JLabel label = new JLabel("Hello World");
		FrameHelper.createMainFrame("Hello Frame", label, true);
  System.out.println("done!");
	}

}
