package com.fairyteller.utilities.concurrent.workflow;

public class DefaultWorkflowElement implements WorkflowElement {

	private String name;
	private UIRunnable uiRunnable;
	private MainRunnable mainRunnable;
	private UIHandler uiHandler;
	private int mainRunnableTimeout;
	
	public DefaultWorkflowElement(){
		this.mainRunnableTimeout = 8000;
	}
	
	public DefaultWorkflowElement(int stateIndex, String name,
			UIRunnable uiRunnable, MainRunnable mainRunnable,
			int mainRunnableTimeout, UIHandler uiHandler) {
		super();
		this.name = name;
		this.uiRunnable = uiRunnable;
		this.mainRunnable = mainRunnable;
		this.mainRunnableTimeout = mainRunnableTimeout;
		this.uiHandler = uiHandler;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UIRunnable getUiRunnable() {
		return uiRunnable;
	}
	public void setUiRunnable(UIRunnable uiRunnable) {
		this.uiRunnable = uiRunnable;
	}
	public MainRunnable getMainRunnable() {
		return mainRunnable;
	}
	public void setMainRunnable(MainRunnable mainRunnable) {
		this.mainRunnable = mainRunnable;
	}
	public int getMainRunnableTimeout() {
		return mainRunnableTimeout;
	}
	public void setMainRunnableTimeout(int mainRunnableTimeout) {
		this.mainRunnableTimeout = mainRunnableTimeout;
	}
	
	@Override
	public UIHandler getUiHandler() {
		return this.uiHandler;
	}
	
	public void setUiHandler(UIHandler handler){
		this.uiHandler = handler;
	}
	
	
}
