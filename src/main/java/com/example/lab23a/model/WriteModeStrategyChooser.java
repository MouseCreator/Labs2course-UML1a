package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WriteModeStrategyChooser {
    private final List<WriteModeStrategy> strategyList;
    public WriteModeStrategy getStrategy(UserOptions options) {
        return strategyList.stream().filter(s -> s.isApplicable(options)).
                findFirst().orElseThrow(NoSuchElementException::new);
    }
    public WriteModeStrategyChooser() {
        this.strategyList = new ArrayList<>();
        strategyList.add(new WriteModeShuffleStrategy());
        strategyList.add(new WriteModeStrategy());
    }
}
