package com.example.lab23a;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.example.lab23a.model.SetIndex;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.StudyTerm;
import com.example.lab23a.model.TermList;

/**
 * Class that controls study set editor
 */
public class EditorController extends AttachedToStudySetIndexController implements Initializable{


    @FXML
    private TextField setNameField;
    
    @FXML
    private ScrollPane rightScrollPane;

    @FXML
    private ListView<Pane> termCreatorList;

    private FXMLLoader itemLoader;
    
    private int elementCount = 0;
    private int selectedIndex = -1;
    private Pane currentPane;
    
    private boolean hasUnsavedChanges = false;
    
    final private KeyCombination keyCombinationFastSave = new KeyCodeCombination(
    		KeyCode.S, KeyCombination.CONTROL_DOWN);
    final private KeyCombination keyCombinationNewTerm = new KeyCodeCombination(
    		KeyCode.N, KeyCombination.CONTROL_DOWN);
    final private KeyCombination keyCombinationLearn = new KeyCodeCombination(
    		KeyCode.L, KeyCombination.CONTROL_DOWN);
    
    
    
    private final LinkedList<EditItemController> controllerList = new LinkedList<>();

    private void setKeyEvent() {
    	rightScrollPane.setOnKeyPressed(event -> {
			if (keyCombinationFastSave.match(event)) {
				onSave();
			}
			else if (keyCombinationNewTerm.match(event)) {
				addEmptyTerm();
			}
			else if (keyCombinationLearn.match(event)) {
				onSaveAndLearn();
			}
			else if (event.getCode().equals(KeyCode.DELETE)) {
				deleteTerm();
			}
		});
    }

	public EditorController() {
		super();
	}
	public EditorController(SetIndex index) {
		super(index);
	}
	private void initIndexAndTerms() {
		this.setNameField.setText(index.getName());
		this.initTerms();
	}
	
	private void initTerms() {
		this.termCreatorList.getItems().clear();
		controllerList.clear();
		TermList list = FileBuilder.readTerms(index.getID());
		this.selectedIndex = -1;
		this.elementCount = 0;
		for (StudyTerm t : list) {
			this.addTerm(t);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		termCreatorList.getSelectionModel().selectedItemProperty().addListener((arg01, arg11, arg2) -> {
			currentPane = termCreatorList.getSelectionModel().getSelectedItem();
			selectedIndex = termCreatorList.getSelectionModel().getSelectedIndex();
		});
		setKeyEvent();
		
	}
	public FXMLLoader loadItem()throws IOException  {
		itemLoader = new FXMLLoader();
		itemLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("SetEditItem")));
		termCreatorList.getItems().add(itemLoader.load());
		return itemLoader;
	}

	/**
	 * Adds new empty term in the end of the list
	 */
	public void addEmptyTerm() {
		hasUnsavedChanges = true;
		EditItemController editItem = initEmptyTerm();
		controllerList.add(editItem);
	}
	private EditItemController initEmptyTerm() {
		try {
			itemLoader = loadItem();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EditItemController editItem = itemLoader.getController();
		editItem.setNumber(++elementCount);
		editItem.setEditor(this);
		return editItem;
		
	}
	private void addTerm(StudyTerm term) {
		EditItemController editItem = initEmptyTerm();
		editItem.fromTerm(term);
		controllerList.add(editItem);
	}

	/**
	 * Deselects the selected term
	 */
	public void deselect() {
		termCreatorList.getSelectionModel().clearSelection();
	}

	/**
	 * Moves selected item one item down if possible
	 */
	public void pushDown()  {
		if (selectedIndex >= elementCount - 1|| selectedIndex == -1)
			return;
		push(selectedIndex, selectedIndex + 1);
	}

	/**
	 * Moves selected item one item up if possible
	 */
	public void pushUp()  {
		if (selectedIndex < 1)
			return;
		push(selectedIndex, selectedIndex - 1);
	}
	
	private void push(int current, int neighbor) {
		hasUnsavedChanges = true;
		swapPanes(current, neighbor);
		swapControllers(current, neighbor);
	}
	
	private void swapPanes(int current, int neighbor) {
		Pane next = termCreatorList.getItems().get(neighbor);	
		termCreatorList.getItems().set(neighbor, currentPane);
		termCreatorList.getItems().set(current, next);
		currentPane = termCreatorList.getItems().set(current, next);
		termCreatorList.getSelectionModel().select(neighbor);
	}
	
	private void swapControllers(int index1, int index2) {
		EditItemController temp = this.controllerList.get(index1);
		this.controllerList.set(index1, this.controllerList.get(index2));
		this.controllerList.set(index2, temp);
		int tempNumber = this.controllerList.get(index1).getNumber();
		this.controllerList.get(index1).setNumber(this.controllerList.get(index2).getNumber());
		this.controllerList.get(index2).setNumber(tempNumber);
	}

	/**
	 * Removes the selected term from the list
	 */
	public void deleteTerm() {
		hasUnsavedChanges = true;
		if (selectedIndex == -1)
			return;
		int current = selectedIndex;
		termCreatorList.getItems().remove(current);
		
		--elementCount;
		for (int i = current + 1; i <= elementCount; i++) {
			controllerList.get(i).setNumber(controllerList.get(i).getNumber()-1);
		}
		controllerList.remove(current);
	}

	/**
	 * Saves the study set to user data and file system
	 */
	public void onSave() {
		hasUnsavedChanges = false;
		index.setName(getStudyIndexName());
		TermList terms = genTermList();
		index.setElementsMastered(terms.calculateMasteredCount());
		index.setElementsCount(elementCount);
		
		parentController.getUserData().insertNewSet(index);
		
		FileBuilder.writeTerms(index.getID(), terms);
	}

	/**
	 *
	 * @return the name of created study set
	 */
	public String getStudyIndexName() {
		String name = setNameField.getText();
		if (name == null)
			return "";
		return name;
	}

	/**
	 * Saves all terms and loads set learn page
	 */
	public void onSaveAndLearn() {
		onSave();
		getParent().loadPage(new WriteModeController().load(parentController, index));
	}
	
	private TermList genTermList() {
		TermList list = new TermList();
		for (EditItemController c : controllerList) {
			list.add(c.toTerm());
		}
		return list;
	}

	private void deleteSet() {
		this.getParent().getUserData().removeStudySet(index);
		this.getParent().loadPage(new SetOpenController().load(parentController));
	}

	/**
	 * Removes the set and loads set opener
 	 */
	public void deleteSetAndConfirm() {
		this.getParent().displayDeletionPopUp(this::deleteSet);
	}
	
	private void saveUnsavedChangesConfirm() {
		this.getParent().displaySavingPopUp(this::onSaveAndClose, this::onNotSaveAndClose);
	}
	private void onSaveAndClose() {
		this.onSave();
		this.getParent().proceedClosingOperation();
	}
	private void onNotSaveAndClose() {
		this.getParent().proceedClosingOperation();
	}
	@Override
	public void initContent() {
		this.initIndexAndTerms();
		hasUnsavedChanges = false;
	}
	
	@Override
	public void onClose() {
		super.onClose();
	}
	@Override
	public boolean onCloseRequest() {
		if (hasUnsavedChanges) {
			saveUnsavedChangesConfirm();
			hasUnsavedChanges = false;
			return false;
		}
    	return true;
    }

	/**
	 * Specifies that editor controller has unsaved changes and cannot be safely left
	 */
	public void addUnsavedChanges() {
		this.hasUnsavedChanges = true;
	}

	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("SetEditor");
	}
	
	
}
