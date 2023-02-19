package com.example.lab23a;

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
public class FolderViewController implements Initializable  {
	private FolderList folders;

	private boolean isToAddIndex = false;
	
	private WorkspaceController parent;
	
	private SetIndex toAdd;
	
	@FXML
    private ListView<Folder> folderListView;

    @FXML
    private TextField newFolderNameField;
    @FXML
    private Label commentLabel;
	public void loadData(WorkspaceController from, FolderList folders) {
		this.parent = from;

		this.folders = folders.sortByName();
		this.folderListView.getItems().clear();
		this.folderListView.getItems().addAll(this.folders.asArrayList());
	}
	
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
		parent.loadPage(new SetOpenController(folder));
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

	public void reinitialize() {
		this.commentLabel.setText("");
		this.newFolderNameField.clear();
		
	}
}
