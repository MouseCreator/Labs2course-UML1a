package com.example.lab23a.Server;

import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.StudySet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSParser {

    public static String fromStudyIndex(SetIndex index) {
        ObjectMapper mapper = new ObjectMapper();
        SetIndexParsable p = new SetIndexParsable(index);
        String result;
        try {
            result = mapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static SetIndex toStudyIndex(String jString) {
        ObjectMapper mapper = new ObjectMapper();
        SetIndexParsable parsed;
        try {
            parsed = mapper.readValue(jString, SetIndexParsable.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return parsed.toIndex();
    }

    public static StudySet toStudySet(String jString) {
        ObjectMapper mapper = new ObjectMapper();
        StudySet set;
        try {
            set = mapper.readValue(jString, StudySet.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return set;
    }
    public static String fromStudySet(StudySet set) {
        ObjectMapper mapper = new ObjectMapper();
        String jString = "";
        try {
            jString = mapper.writeValueAsString(set);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jString;
    }
}
