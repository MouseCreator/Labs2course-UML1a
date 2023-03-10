package com.example.lab23a;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import com.example.lab23a.model.Dates;
import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndexList;

/**
 * Class that controls opener page
 */
public class SetOpenController extends AttachedToFolderController implements Initializable {
	@FXML
	public ScrollPane scroll;
	@FXML
    private GridPane grid;

	@FXML
    private TextField searchTextField;
    
    @FXML
    private AnchorPane folderInfoPane;

    @FXML
    private Label folderNameLabel;
    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

	private SetIndexList folderStudySets = new SetIndexList();

	@FXML
    public void onEnter(){
       searchStudySets();
    }
    private void initDatePickers() {
    	initDatePicker(datePickerFrom);
    	initDatePicker(datePickerTo);
    }
    
    private void initDatePicker(DatePicker datePicker) {

    	datePicker.setConverter(new StringConverter<>() {

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return Dates.toDateFormat(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return Dates.fromString(string);
				} else {
					return null;
				}
			}
		});
    }
    
    private void getData() {
    	if (folder.getIndex() == Folder.ALL_SETS) {
    		folderStudySets = new SetIndexList(parentController.getUserData().getIndexList());
    	}
    	else {
    		folderStudySets = FileBuilder.readFolder(parentController.getUserData().getIndexList(), folder.getIndex());
    	}
		folderStudySets.reverse();
    }
    public SetOpenController() {
		super();
    }
	public SetOpenController(Folder folder) {
		super(folder);
	}
    
    private void loadGrid() {
		getData();
		updateGrid(folderStudySets);
    }
	/**
	 * Adds items to the grid in the order they are in the active study sets
	 */
	private void updateGrid(SetIndexList activeStudySets) {
		int column = 0;
		int row = 1;
		
		try {
			grid.getChildren().clear();
			for (int i = 0; i < activeStudySets.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("Item")));
				AnchorPane anchorPane = initItem(fxmlLoader);
				
				ItemController itemController = fxmlLoader.getController();
				itemController.setData(activeStudySets.get(i));

				int setsPerRow = 3;
				if (column == setsPerRow) {
					column = 0;
					row++;
				}
				
				grid.add(anchorPane, column++, row);
				
				grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
				
				grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
				
				GridPane.setMargin(anchorPane, new Insets(10));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private AnchorPane initItem(FXMLLoader fxmlLoader) throws IOException {
		
		fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("Item")));
		AnchorPane anchorPane = fxmlLoader.load();
		anchorPane.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					parentController.loadPage(new SetInfoController().load(parentController,
							((ItemController)fxmlLoader.getController()).getIndex()));
				}
			}
		});
		return anchorPane;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initDatePickers();
	}
	/**
	 * Activates, when 'Search' button is pressed or date pickers changed
	 * Shows only the sets that fits the searching criteria; Sorted by index (creation date) in reversed order.
	 */
	@FXML
	public void searchStudySets() {
		String searchBy = searchTextField.getText();
		SetIndexList activeStudySets = folderStudySets.searchByName(searchBy);

		LocalDate fromDate = datePickerFrom.getValue();
		if (fromDate != null) {
			activeStudySets = activeStudySets.searchAfter(fromDate);
		}
		LocalDate toDate = datePickerTo.getValue();
		if (toDate != null) {
			activeStudySets = activeStudySets.searchBefore(toDate);
		}
		updateGrid(activeStudySets);
	}
	
	private void reload() {
		this.searchTextField.clear();
		this.datePickerFrom.getEditor().clear();
		this.datePickerTo.getEditor().clear();
	}
	
	@Override
	public void initContent() {
		reload();
		initFolderPane();
		loadGrid();
	}
	
	private void initFolderPane() {
		if (this.folder.getIndex() == Folder.ALL_SETS) {
			this.folderInfoPane.setVisible(false);
		}
		else {
			this.folderInfoPane.setVisible(true);
			this.folderNameLabel.setText(getFolderTitle());
		}
	}
	private String getFolderTitle() {
		return "Folder: " + folder.getName();
	}
	public void deleteFolder() {
		this.getParent().displayFolderDeletionPopUp(this::onDeletionConfirm);
		
	}
	private void onDeletionConfirm() {
		this.getParent().getUserData().removeFolder(this.folder);
		this.getParent().loadPage(new SetOpenController().load(parentController));
	}
	
	public void test() {
		System.out.println("Hello");
	}

	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("SetOpen");
	}
	@Override
	public SetOpenController load(WorkspaceController parent, Folder folder) {
		return (SetOpenController) super.load(parent, folder);
	}
	

}
