package com.fairyteller.swing;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Convenience class with useful methods for quickly checking out a swing component
 * Use those inside a main()
 * @author tphilipakis
 *
 */
public class FrameHelper {
	
	/**
	 * useful for quickly bringing up a swing JFrame in main() method.
	 * Performs a pack()
	 * 
	 * @param title - title of the JFrame
	 * @param myComponent - the component to display inside the frame
	 * @param exitOnClose - should we perform a System.exit() after a window close operation
	 * @return
	 */
	public static JFrame createMainFrameAndPack(String title, JComponent myComponent, boolean exitOnClose){
		final JFrame frame = new JFrame(title);
		if(exitOnClose){
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(myComponent, BorderLayout.CENTER);
		frame.getContentPane().add(contentPane);
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				frame.pack();
				frame.setVisible(true);
			}
		});
		return frame;
	}
	/**
	 * useful for quickly bringing up a 640x480 swing JFrame in main() method.
	 * 
	 * @param title - title of the JFrame
	 * @param myComponent - the component to display inside the frame
	 * @param exitOnClose - should we perform a System.exit() after a window close operation
	 * @return
	 */
	public static JFrame createMainFrame(String title, JComponent myComponent, boolean exitOnClose){
		return createMainFrame(title, myComponent, 640, 480, exitOnClose);
	}
	
	/**
	 * useful for quickly bringing up a <b>width</b> x <b>height</b> swing jframe in main() method.
	 * 
	 * @param title - title of the jframe
	 * @param myComponent - the component to display inside the frame
	 * @param width - width of the jframe in pixels
	 * @param height - height of the jframe in pixels
	 * @param exitOnClose - should we perform a System.exit() after a window close operation
	 * @return
	 */
	public static JFrame createMainFrame(String title, JComponent myComponent,final int width, final int height, boolean exitOnClose){
		final JFrame frame = new JFrame(title);
		if(exitOnClose){
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(myComponent, BorderLayout.CENTER);
		frame.getContentPane().add(contentPane);
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				frame.setSize(width, height);
				frame.setVisible(true);
			}
		});
		return frame;
	}
}
