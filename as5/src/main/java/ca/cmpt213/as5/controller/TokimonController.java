package ca.cmpt213.as5.controller;

import ca.cmpt213.as5.model.Tokimon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class that build Api and Handles Request
 */
@RestController
public class TokimonController {
    List<Tokimon> TokimonReadFromJson;
    HashMap<Integer, Tokimon> HashMapTokimon = new HashMap<>();

    //Loading tokimon into hash Table
    public void LoadTokimonInHashSet() {
        for (int i = 0; i < TokimonReadFromJson.size(); i++) {
            HashMapTokimon.put(TokimonReadFromJson.get(i).getId(), TokimonReadFromJson.get(i));
        }
    }

    //Function to load tokimon form Json file
    public void LoadTokimonFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TokimonReadFromJson = mapper.readValue(new File("data/tokimon.json"), new TypeReference<List<Tokimon>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Returns all tokimons from Json File
    @GetMapping("/api/tokimon/all")
    public List<Tokimon> allTokimons() {
        LoadTokimonFromJson();
        return TokimonReadFromJson;
    }

    //Returns tokimon with specific id
    @GetMapping("/api/tokimon/{id}")
    public Tokimon getTokimon(@PathVariable int id) throws IOException {

        if (id < 0) {
            throw new onIllegalArgumentException("");
        }

        LoadTokimonFromJson();
        LoadTokimonInHashSet();

        if (!HashMapTokimon.containsKey(id)) {
            throw new OrderNotFoundException("");
        }

        return HashMapTokimon.get(id);
    }

    //Adds a new Tokimon
    @PostMapping("/api/tokimon/add")
    public void addTokimon(@RequestBody Tokimon newTokimon) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type ListOfTokimonType = new TypeToken<List<Tokimon>>() {
        }.getType();
        FileReader fileReader = new FileReader("data/tokimon.json");
        List<Tokimon> tokimonList = gson.fromJson(fileReader, ListOfTokimonType);
        fileReader.close();

        if (null == tokimonList) {
            tokimonList = new ArrayList<>();
        }
        Tokimon TokimonToBeAdded = new Tokimon();

        TokimonToBeAdded.setName(newTokimon.getName());
        TokimonToBeAdded.setAbility(newTokimon.getAbility());
        TokimonToBeAdded.setColor(newTokimon.getColor());
        TokimonToBeAdded.setHeight(newTokimon.getHeight());

        Random idToAssign = new Random();
        int rand = idToAssign.nextInt(10000);

        TokimonToBeAdded.setId(rand);
        TokimonToBeAdded.setWeight(newTokimon.getWeight());
        TokimonToBeAdded.setStrength(newTokimon.getStrength());


        tokimonList.add(TokimonToBeAdded);

        FileWriter fileWriter = new FileWriter("data/tokimon.json");
        gson.toJson(tokimonList, fileWriter);
        fileWriter.close();

    }
    //Delete tokimon
    @DeleteMapping("/api/tokimon/{id}")
    public void deletePerson(@PathVariable int id) throws IOException {
        LoadTokimonFromJson();
        LoadTokimonInHashSet();

        if (!HashMapTokimon.containsKey(id)) {
            throw new OrderNotFoundException("Id Doesn't Exist");
        }


        // System.out.println(HashMapTokimon.get(id));
        HashMapTokimon.remove(id);

        for (int i = 0; i < TokimonReadFromJson.size(); i++) {
            if (TokimonReadFromJson.get(i).getId() == id)
                TokimonReadFromJson.remove(i);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter("data/tokimon.json");
        gson.toJson(TokimonReadFromJson, fileWriter);
        fileWriter.close();
    }

    //Handles Errors
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id doesn't Exist !!")
    public class OrderNotFoundException extends RuntimeException {

        public OrderNotFoundException(String message) {

            super(message);
        }
    }

    //Handles Errors
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The id you entered is negative. Please Enter a positive number!!")
    public class onIllegalArgumentException extends RuntimeException {

        public onIllegalArgumentException(String message) {
            super(message);
        }
    }

    // Handles Errors
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The id you entered is not a number. Please Enter a positive number!!")
    public class NumberFormatException extends RuntimeException {

        public NumberFormatException(String message) {
            super(message);
        }
    }

}
