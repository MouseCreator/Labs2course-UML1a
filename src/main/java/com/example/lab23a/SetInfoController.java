package com.example.lab23a;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.lab23a.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * Page with information about study set, including its name, progress and all of the terms
 */
public class SetInfoController extends AttachedToStudySetIndexController implements Initializable{

    public SetInfoController() {
		super();
	}

	/**
	 * All terms of the study set
	 */
	private TermList studyTerms;
    private final ObservableList<StudyTerm> observableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<StudyTerm, Integer> idColumn;
	@FXML
    private TableColumn<StudyTerm, String> definitionColumn;

    @FXML
    private Pane itemPane;

    @FXML
    private TableColumn<StudyTerm, String> statusColumn;

    @FXML
    private TableColumn<StudyTerm, String> termColumn;

    @FXML
    private TableView<StudyTerm> termTable;
    @FXML
    private Button removeFromThisFolderBtn;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
		termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
		definitionColumn.setCellValueFactory(new PropertyValueFactory<>("definition"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("progressString"));
	}
	
	
	private void initItem(SetIndex from) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("item.fxml"));
		itemPane.getChildren().add(fxmlLoader.load());
		
		ItemController itemController = fxmlLoader.getController();
		itemController.setData(from);
	}	
	private void loadTerms() throws IOException {
		studyTerms = FileBuilder.readTerms(index.getID());
		displayTerms();
	}
	
	private void displayTerms() {
		observableList.clear();
		observableList.addAll(studyTerms.asArrayList());
		termTable.setItems(observableList);
	}
	@Override
	public void initContent() {
		try {
			initItem(this.index);
			loadTerms();
			initFolderButton();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void initFolderButton() {
		this.removeFromThisFolderBtn.setVisible(getParent().getLastActive().getIndex() != Folder.ALL_SETS);
	}

	/**
	 * Moves to learning screen
	 */
	public void startLearning() {
		getParent().loadPage(new WriteModeController().load(parentController, index));
	}

	/**
	 * Removes the progress from all terms of the set
	 * @throws IOException - progress panel could not be created
	 */
	public void onRefresh() throws IOException {
		studyTerms.refresh();
		index.setElementsMastered(0);
		initItem(index);
		displayTerms();
	}

	/**
	 * Moves to the editor screen
	 */
	public void onEdit() {
		getParent().loadPage(new EditorController(index));
	}

	/**
	 * Adds set index to specified folder
	 */
	public void onAddToFolder() {
		getParent().openAdditionalWindow(new FolderViewController().load(parentController, index));
	}

	/**
	 * Removes set index from the folder it was openned from
	 */
	public void onRemoveFromFolder() {
		getParent().getUserData().removeSetFromFolder(index, getParent().getLastActive());
		getParent().refreshLastActive();
		getParent().loadPage(new SetInfoController().load(parentController, index));
	}
	@Override
	public void onClose() {
		super.onClose();
		FileBuilder.writeTerms(index.getID(), studyTerms);
	}
	@FXML
	public void convertToPDF() {
		openSavePDFDialog();
	}

	private void openSavePDFDialog() {
		parentController.openAdditionalWindow(new ExportController().load(parentController, index, studyTerms));
	}

	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("SetInfo");
	}
}
