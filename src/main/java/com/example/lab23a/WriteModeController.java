package com.example.lab23a;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.lab23a.model.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WriteModeController extends AttachedToStudySetIndexController implements Initializable {


    @FXML
    private Pane changeAnswerPane;

    @FXML
    private Label commentLabel;

    @FXML
    private ProgressBar currentPartBar;

    @FXML
    private Label definitionLabel;


    @FXML
    private TextField termField;
    
    @FXML
    private ProgressBar totalBar;
    
    
    private WriteModeTermsContainer learnMode;
    private WriteAnswerChecker checker;
    
    private StudyTerm currentTerm;
    
    private final Color rightAnswerColor = Color.GREEN;
    private final Color wrongAnswerColor = Color.RED;
    
    private String userAnswer;
    
    private WriteResultsController subcontroller;
    private Stage substage;
    private boolean isEnteredAnswer = false;

	public WriteModeController(SetIndex index) {
		super(index);
	}

	@FXML
    public void onEnter(){
       confirmAnswer();
    }
    
	@Override
	public void initContent() {
		checker = new WriteAnswerChecker();
		learnMode = new WriteModeTermsContainer(FileBuilder.readTerms(index.getID()),
				getParent().getUserData().getStyle());
		refreshInterface();
		loadNextTerm();
	}
	
	private void refreshInterface() {
		updateProgressBars();
	}
	
	private void loadNextTerm() {
		hideExtra();
		isEnteredAnswer = false;
		termField.clear();
		if (learnMode.isPeriodEnd()) {
			toTempResults();
		}
		else {
			loadTerm();
		}
	}
	private void updateLastStudied() {
		index.updateLastStudied();
		getParent().getUserData().getStyle().updateStreak();
	}
	private void toTempResults() {
		if (learnMode.isFullStudied()) {
			updateLastStudied();
			updateTermsFile();
		}
		writeResults();
		showResultsPopWindow();
	}
	
	private void updateTermsFile() {
		FileBuilder.writeTerms(this.index.getID(), this.learnMode.getStudyResult());
	}

	private void loadTerm() {
		currentTerm = learnMode.getNextTerm();
		checker.setTerm(currentTerm.getTerm());
		this.definitionLabel.setText(currentTerm.getDefinition());
	}
	
	private void hideExtra() {
		changeAnswerPane.setVisible(false);
		commentLabel.setVisible(false);
	}

	/**
	 * Checks if the entered answer is correct, displays corresponding massage to user
	 */
	public void confirmAnswer() {
		if (isEnteredAnswer)
			return;
		userAnswer = termField.getText();
		if (checker.checkAnswer(termField.getText())) {
			gotCorrect();
		}
		else {
			gotWrong();
		}
	}
	
	private void setLabelToCorrectAnswer() {
		this.commentLabel.setText(checker.getRandomCorrectAnswerString());
		this.commentLabel.setTextFill(rightAnswerColor);
		this.commentLabel.setVisible(true);
	}
	private void setLabelToWrongAnswer() {
		this.commentLabel.setText(checker.getWrongAnswerString(userAnswer));
		this.commentLabel.setTextFill(wrongAnswerColor);
		this.commentLabel.setVisible(true);
	}
	
	private void showExtra() {
		this.changeAnswerPane.setVisible(true);
	}
	
	private void gotCorrect() {
		setLabelToCorrectAnswer();
		learnMode.onCorrectEntered(checker, currentTerm);
		updateProgressBars();
		isEnteredAnswer = true;
		delay(this::loadNextTerm);
	}
	
	private void gotWrong() {
		setLabelToWrongAnswer();
		termField.clear();
		showExtra();
	}

	/**
	 * Sets progress bars to user's actual progress
	 */
	public void updateProgressBars() {
		this.currentPartBar.setProgress(learnMode.getCurrentProgress());
		this.totalBar.setProgress(learnMode.getTotalProgress());
	}
	
	private void delay(Runnable continuation) {
	      Task<Void> sleeper = new Task<>() {
			  @Override
			  protected Void call(){
				  try {
					  Thread.sleep(1000);
				  } catch (InterruptedException ignored) {
				  }
				  return null;
			  }
		  };
	      sleeper.setOnSucceeded(event -> continuation.run());
	      new Thread(sleeper).start();
	}

	/**
	 * Loads terms that are not learned yet
	 */
	public void continueLearning() {
		learnMode.reinit();
		refreshInterface();
		this.loadNextTerm();
	}

	/**
	 * Sets all terms to Not Learned and restarts the scene
	 */
	public void restart() {
		initContent();
	}

	/**
	 * Returns user to Set Info page
	 */
	public void goBack() {
		this.getParent().loadPage(new SetInfoController(index));
	}

	private void showResultsPopWindow() {
			subcontroller.loadData(this, learnMode);
			substage.show();
	}
	private void initPopUp() {
		try {
			Parent root;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("WritePartResults")));
			root = fxmlLoader.load();
			Scene scene = new Scene(root, 720, 480);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			substage = new Stage();
			substage.setScene(scene);
			
			substage.setTitle("Your results");
			substage.setResizable(false);
			
			subcontroller = fxmlLoader.getController();
			
			Image icon = new Image(FileBuilder.getIconDestination());
			substage.getIcons().add(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void writeResults() {
		TermList list = learnMode.getStudyResult();
		this.index.setElementsMastered(list.calculateMasteredCount());
		getParent().getUserData().getIndexList().insert(index);
		FileBuilder.writeTerms(index.getID(), list);
	}

	/**
	 * Program's response to 'It was right answer' button
	 */
	public void onChangeToRightAnswer() {
		this.checker.changeToRightAnswer();
		this.gotCorrect();
	}

	/**
	 * Program's response to 'Set this as right answer' button
	 */
	public void onChangeToRightAnswerAndSet() {
		currentTerm.setTerm(userAnswer);
		onChangeToRightAnswer();
	}
	@Override
	public void onClose() {
		this.substage.close();
	}
	@Override
	public boolean onCloseRequest() {
		boolean canLeaveWithNoDataLoss = this.learnMode.isPeriodEnd();
		if (!canLeaveWithNoDataLoss)
			this.getParent().displayExitPopUp(()->getParent().proceedClosingOperation());
		return canLeaveWithNoDataLoss;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initPopUp();
	}
	@Override
	public String getDestination() {
		return FileBuilder.FXMLDestination("Write");
	}
}
