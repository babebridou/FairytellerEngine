package com.fairyteller.utilities.concurrent.workflow;

public class DefaultWorkflowElement implements WorkflowElement {

	private String name;
	private UIRunnable startupUiRunnable;
	private UIRunnable finishUiRunnable;
	private MainRunnable mainRunnable;
	private UIRunnable timeoutUiRunnable;
	private UIHandler uiHandler;
	private int mainRunnableTimeout;
	
	public DefaultWorkflowElement(){
		this.mainRunnableTimeout = 8000;
	}
	
	public DefaultWorkflowElement(int stateIndex, String name,
			UIRunnable startupUiRunnable, MainRunnable mainRunnable, UIRunnable finishUiRunnable,
			int mainRunnableTimeout, UIHandler uiHandler, UIRunnable timeoutUiRunnable) {
		super();
		this.name = name;
		this.startupUiRunnable = startupUiRunnable;
		this.finishUiRunnable = finishUiRunnable;
		this.mainRunnable = mainRunnable;
		this.mainRunnableTimeout = mainRunnableTimeout;
		this.uiHandler = uiHandler;
		this.timeoutUiRunnable = timeoutUiRunnable;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UIRunnable getStartupUiRunnable() {
		return startupUiRunnable;
	}
	public void setStartupUiRunnable(UIRunnable startupUiRunnable) {
		this.startupUiRunnable = startupUiRunnable;
	}
	
	public UIRunnable getFinishUiRunnable() {
		return finishUiRunnable;
	}

	public void setFinishUiRunnable(UIRunnable finishUiRunnable) {
		this.finishUiRunnable = finishUiRunnable;
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
	
	@Override
	public UIRunnable getTimeoutUiRunnable() {
		return this.timeoutUiRunnable;
	}
	
	public void setTimeoutUiRunnable(UIRunnable timeoutUiRunnable) {
		this.timeoutUiRunnable = timeoutUiRunnable;
	}
	
	
	
	
	
}
