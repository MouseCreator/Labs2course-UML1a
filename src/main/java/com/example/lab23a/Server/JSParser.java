package com.example.lab23a.Server;

import com.example.lab23a.model.SetIndex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSParser {

    public static void studyIndex(SetIndex index) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(index);
            SetIndex a = mapper.readValue(result, SetIndex.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
