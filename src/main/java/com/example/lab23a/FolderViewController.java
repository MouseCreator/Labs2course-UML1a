package com.example.lab23a;

import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.Folder;
import com.example.lab23a.model.FolderList;
import com.example.lab23a.model.SetIndex;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for additional window that contains list of all folders
 */
public class FolderViewController extends AdditionalWindowController implements Initializable  {
	/**
	 * All folders that will be shown (loaded from UserData)
	 */
	private FolderList folders;

	private boolean isToAddIndex = false;
	
	private SetIndex toAdd;
	
	@FXML
    private ListView<Folder> folderListView;

    @FXML
    private TextField newFolderNameField;
    @FXML
    private Label commentLabel;

	/**
	 * Loads and displays folder list
	 * @param folders - all user's folders
	 */
	public void loadData(FolderList folders) {
		this.folders = folders.sortByName();
		this.folderListView.getItems().clear();
		this.folderListView.getItems().addAll(this.folders.asArrayList());
	}

	/**
	 *
	 * @param toAdd - set index, that has to be added to a folder
	 */
	public void toAddIndex(SetIndex toAdd) {
		this.isToAddIndex = true;
		this.toAdd = toAdd;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		commentLabel.setText("");
		folderListView.setOnMouseClicked(click -> {

			if (click.getClickCount() == 2) {
				Folder selected = folderListView.getSelectionModel().getSelectedItem();
				if (isToAddIndex) {
					addSetToFolder(toAdd, selected);
				} else {
					openFolderInWorkspace(selected);
				}
			}
		});
	}
	
	private void openFolderInWorkspace(Folder folder) {
		parent.loadPage(new SetOpenController().load(parent, folder));
		close();
	}
	
	private void addSetToFolder(SetIndex index, Folder folder) {
		isToAddIndex = false;
		parent.getUserData().addSetToFolder(index, folder);
		close();
	}
	
	public void close() {
		((Stage)this.commentLabel.getScene().getWindow()).close();
	}

	/**
	 * Creates new empty folder in the folder list if text field has proper name
	 * Otherwise displays error message
	 */
	public void onNewFolder() {
		if (!newFolderNameField.getText().trim().equals("")) {
			Folder toAdd = parent.getUserData().appendNewFolder(newFolderNameField.getText());
			this.folderListView.getItems().add(toAdd);
			commentLabel.setText("");
		}
		else {
			commentLabel.setText("Folder name can't be empty");
		}
	}

	/**
	 * Resets interface of the folder view
	 */
	public void reinitialize() {
		this.commentLabel.setText("");
		this.newFolderNameField.clear();
	}

	@Override
	public FolderViewController load(WorkspaceController parent) {
		return defaultController(parent);
	}
	public FolderViewController load(WorkspaceController parent, SetIndex index) {
		FolderViewController result = defaultController(parent);
		result.toAddIndex(index);
		return result;
	}

	private FolderViewController defaultController(WorkspaceController parent) {
		FolderViewController result = (FolderViewController) super.load(parent);
		result.loadData(parent.getUserData().getFolderList());
		result.reinitialize();
		result.setWidth(256);
		result.setHeight(512);
		return result;
	}

	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("FolderList");
	}

	@Override
	public String getTitle() {
		return "Folders";
	}


}
