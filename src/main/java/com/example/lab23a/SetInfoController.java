package com.example.lab23a;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.lab23a.PDF.PDFConvertor;
import com.example.lab23a.PDF.PDFParams;
import com.example.lab23a.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

public class SetInfoController extends AttachedToStudySetIndexController implements Initializable{

    public SetInfoController() {
		super();
	}

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
	public void loadTerms() throws IOException {
		studyTerms = FileBuilder.readTerms(index.getID());
		displayTerms();
	}
	
	public void displayTerms() {
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
	public void initFolderButton() {
		this.removeFromThisFolderBtn.setVisible(getParent().getLastActive().getIndex() != Folder.ALL_SETS);
	}
	public void startLearning() {
		getParent().loadAttachedToIndex(Pages.SET_WRITE, this.index);
	}
	
	public void onRefresh() throws IOException {
		studyTerms.refresh();
		index.setElementsMastered(0);
		initItem(index);
		displayTerms();
	}
	public void onEdit() {
		getParent().loadAttachedToIndex(Pages.SET_EDITOR, index);
	}
	public void onAddToFolder() {
		getParent().displayFoldersWindow();
		getParent().getFolderView().toAddIndex(index);
	}
	public void onRemoveFromFolder() {
		getParent().getUserData().removeSetFromFolder(index, getParent().getLastActive());
		getParent().refreshLastActive();
		getParent().loadAttachedToIndex(Pages.SET_INFO, index);
	}
	@Override
	public void onClose() {
		super.onClose();
		FileBuilder.writeTerms(index.getID(), studyTerms);
	}
	@FXML
	public void convertToPDF() {
		//savePDF();
		openSavePDFDialog();
	}
	private ExportController dialogController;
	private void openSavePDFDialog() {
		try {
			Parent root;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("PDFPopUp")));
			root = fxmlLoader.load();
			Scene scene = new Scene(root, 512, 384);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			final Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Export PDF");
			dialogStage.setResizable(false);
			dialogController = fxmlLoader.getController();
			dialogController.setParent(this);
			dialogController.init(index, studyTerms);
			Image icon = new Image(FileBuilder.getIconDestination());
			dialogStage.getIcons().add(icon);
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
