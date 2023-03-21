package com.example.lab23a.model.builder;

import com.example.lab23a.model.SetIndex;

public interface Visitor {
    void visitSetIndex(SetIndex index);
    Object get();
}
