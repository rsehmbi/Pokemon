package ca.cmpt213.as5.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ReadJson {

    public void readthefile() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Tokimon>> mapType = new TypeReference<List<Tokimon>>() {
        };
        List<Tokimon> jsontoTokimonList;
        {
            try {
                jsontoTokimonList = objectMapper.readValue("tokimon.json", mapType);
                jsontoTokimonList.forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
