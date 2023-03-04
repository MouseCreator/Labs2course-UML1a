package com.example.lab23a;

/**
 * Template class for pop-up windows
 */
public class PopUpTemplateWindow {
	/**
	 * Name of the window
	 */
	protected String title;
	/**
	 * Message to show on the window
	 */
	protected String message;
	/**
	 * Action, when user accepted
	 */
	protected Runnable onConfirm = ()->{};
	/**
	 * Action, when user declined
	 */
	protected Runnable onDecline= ()->{};
	public PopUpTemplateWindow() {
		this.title = "Pop up";
		this.message = "Are you sure?";
	}
	public PopUpTemplateWindow(String title, String message) {
		this.title = title;
		this.message = message;
	}
	/**
	 * Changes confirm action
	 * @param onConfirmRunnable - action to do, when user accepted
	 */
	public void setOnConfirm(Runnable onConfirmRunnable) {
		onConfirm = onConfirmRunnable;
	}

	/**
	 * Changes decline action
	 * @param onDeclineRunnable - action to do, when user declined
	 */
	public void setOnDecline(Runnable onDeclineRunnable) {
		onDecline = onDeclineRunnable;
	}

	/**
	 *
	 * @return action to do, when user accepted
	 */
	public Runnable getOnConfirm() {
		return onConfirm;
	}

	/**
	 *
	 * @return action to do, when user declined
	 */
	public Runnable getOnDecline() {
		return onDecline;
	}

	/**
	 *
	 * @return name of the window
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 *
	 * @return message, displayed by the window
	 */
	public String getMessage() {
		return this.message;
	}
}
