package com.example.lab23a.model.sort;

import com.example.lab23a.model.Folder;
import com.example.lab23a.model.FolderList;
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.SetIndexList;

import java.util.Comparator;

public class NameSorterVisitor implements SortingVisitor{
    private final Comparator<Folder> folderComparator = Comparator.comparing(Folder::getName);
    private final Comparator<SetIndex> indexComparator = Comparator.comparing(SetIndex::getName);
    @Override
    public void visitSetIndexList(SetIndexList setIndexList) {
        setIndexList.asList().sort(indexComparator);
    }

    @Override
    public void visitFolderList(FolderList folderList) {
        folderList.asList().sort(folderComparator);
    }
}
