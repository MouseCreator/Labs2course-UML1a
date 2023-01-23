
package com.example.lab23a;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.lab23a.model.StudyProgress;
import com.example.lab23a.model.StudyTerm;

public class EditItemController implements Initializable{

    @FXML
    private TextField definitionTextField;

    @FXML
    private Label termNumber;

    @FXML
    private TextField termTextField;
    
    private EditorController editor;
    
    StudyProgress progress = StudyProgress.NOT_LEARNED;
    private int number;
    
    
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
    
    public void fromTerm(StudyTerm term) {
    	this.progress = term.getProgress();
    	termTextField.setText(term.getTerm());
    	definitionTextField.setText(term.getDefinition());
    }
    
    public void setNumber(int number) {
    	this.number = number;
    	this.termNumber.setText("Term " + number);
    }
    public int getNumber() {
    	return number;
    }
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