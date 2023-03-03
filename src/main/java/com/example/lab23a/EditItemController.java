
package com.example.lab23a;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.lab23a.model.StudyProgress;
import com.example.lab23a.model.StudyTerm;

/**
 * Item of the list in editor mode. Can be edited by user and converted to study term
 */
public class EditItemController implements Initializable{

    @FXML
    private TextField definitionTextField;

    @FXML
    private Label termNumber;

    @FXML
    private TextField termTextField;
    
    private EditorController editor;
    
    private StudyProgress progress = StudyProgress.NOT_LEARNED;
    private int number;

	/**
	 *
	 * @return study term, created from content fields
	 */
	public StudyTerm toTerm() {
    	String term = termTextField.getText();
    	if (term == null)
    		term = "";
    	String definition = definitionTextField.getText();
    	if (definition == null)
    		definition = "";
    	return new StudyTerm(number, term, definition, progress);
    }
    
    public void load(int number, StudyTerm term) {
    	setNumber(number);
    	fromTerm(term);
    }

	/**
	 * Sets item's content to term's properties
	 * @param term - term, from which the controller will be created
	 */
	public void fromTerm(StudyTerm term) {
    	this.progress = term.getProgress();
    	termTextField.setText(term.getTerm());
    	definitionTextField.setText(term.getDefinition());
    }

	/**
	 *
	 * @param number - the order of the item in the list
	 */
	public void setNumber(int number) {
    	this.number = number;
    	this.termNumber.setText("Term " + number);
    }

	/**
	 *
	 * @return order of the item
	 */
    public int getNumber() {
    	return number;
    }

	/**
	 *
	 * @param editor - the page, the created the item
	 */
    public void setEditor(EditorController editor) {
    	this.editor = editor;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		definitionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!oldValue.equals(newValue)) {
		    	editor.addUnsavedChanges();
		    }
		});
		
	}

}