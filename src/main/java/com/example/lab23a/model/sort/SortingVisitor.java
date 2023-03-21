package com.example.lab23a.model.sort;

import com.example.lab23a.model.FolderList;
import com.example.lab23a.model.SetIndexList;
import com.example.lab23a.model.builder.Visitor;

public interface SortingVisitor extends Visitor {
    void visitSetIndexList(SetIndexList setIndexList);

    void visitFolderList(FolderList folderList);
}
