package com.example.lab23a.model;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileBuilderTest {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public TermList readTerms() {
        int TEST_SET = 1;
        return FileBuilder.readTermsTest(TEST_SET);
    }@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public SetIndexList readIndex() {
        return FileBuilder.readIndexTest();
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Test
    void testTermsRead() {
        TermList list = readTerms();
        assertEquals(20, list.size());
    }
    @Test
    void testIndexRead() {
        SetIndexList list = readIndex();
        assertEquals(36, list.size());
    }

}