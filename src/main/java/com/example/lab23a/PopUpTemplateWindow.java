package com.example.lab23a;

public class PopUpTemplateWindow {
	protected String title;
	protected String message;
	protected Runnable onConfirm = ()->{};
	protected Runnable onDecline= ()->{};
	public PopUpTemplateWindow() {
		this.title = "Pop up";
		this.message = "Are you sure?";
	}
	public PopUpTemplateWindow(String title, String message) {
		this.title = title;
		this.message = message;
	}
	public void setOnConfirm(Runnable onConfirmRunnable) {
		onConfirm = onConfirmRunnable;
	}
	public void setOnDecline(Runnable onDeclineRunnable) {
		onDecline = onDeclineRunnable;
	}
	public Runnable getOnConfirm() {
		return onConfirm;
	}
	public Runnable getOnDecline() {
		return onDecline;
	}
	public String getTitle() {
		return this.title;
	}
	public String getMessage() {
		return this.message;
	}
}
