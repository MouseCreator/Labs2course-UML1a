package com.example.lab23a;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import com.example.lab23a.model.Dates;
import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndexList;
import org.apache.pdfbox.pdmodel.PDDocument;


public class SetOpenController extends AttachedToFolderController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Button searchBtn;

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
    @FXML
    private Button testBtn;
    @FXML
    public void onEnter(ActionEvent ae){
       searchData();
    }
    
    private final int setsPerRow = 3;
    
    private SetIndexList activeStudySets = new SetIndexList();
    
    private SetIndexList folderStudySets = new SetIndexList();
    
    private void initDatePickers() {
    	initDatePicker(datePickerFrom);
    	initDatePicker(datePickerTo);
    }
    
    private void initDatePicker(DatePicker datePicker) {

    	datePicker.setConverter(new StringConverter<LocalDate>() {

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
    	activeStudySets = folderStudySets;
    	activeStudySets.reverse();
    }
    public SetOpenController(WorkspaceController parent) {
    	super(parent);
    }
    public SetOpenController() {
    	super();
    }
    
    private void loadGrid() {
		getData();
		setGrid();
    }
	/**
	 * Adds items to the grid in the order they are in the active study sets
	 */
	public void setGrid() {
		int column = 0;
		int row = 1;
		
		try {
			grid.getChildren().clear();
			for (int i = 0; i < activeStudySets.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("Item")));
				AnchorPane anchorPane = initItem(fxmlLoader);
				
				ItemController itemController = fxmlLoader.getController();
				itemController.setData(activeStudySets.get(i));
				
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
		
		fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("Item")));
		AnchorPane anchorPane = fxmlLoader.load();
		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		                parentController.loadAttachedToIndex(Pages.SET_INFO,((ItemController)fxmlLoader.getController()).getIndex());;
		            }
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
	 * Shows only sets that fits the criteria in the list
	 */
	public void searchData() {
		String searchBy = searchTextField.getText();
		this.activeStudySets = folderStudySets.searchByName(searchBy);
		
		LocalDate from = datePickerFrom.getValue();
		if (from != null) {
			activeStudySets = activeStudySets.searchAfter(from);
		}
		LocalDate to = datePickerTo.getValue();
		if (to != null) {
			activeStudySets = activeStudySets.searchBefore(to);
		}
		
		setGrid();
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
		this.getParent().displayFolderDeletionPopUp(()->onDeletionConfirm());
		
	}
	private void onDeletionConfirm() {
		this.getParent().getUserData().removeFolder(this.folder);
		this.getParent().loadWithAllFolders(Pages.SET_OPEN);
	}
	
	public void test() {
		System.out.println("Hello");
	}
	

}
