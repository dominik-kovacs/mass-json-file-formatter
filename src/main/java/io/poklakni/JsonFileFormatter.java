package io.poklakni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonFileFormatter {

    public static void format(Path jsonFilePath) throws IOException {

        String jsonString = new String(Files.readAllBytes(jsonFilePath));

        //if the json file contains a json object, map the string to LinkedHashMap to preserve key order
        Class<?> targetType = jsonString.trim().startsWith("[") ? ArrayList.class : LinkedHashMap.class;
        Object json = new ObjectMapper().readValue(jsonString, targetType);

        String formattedJsonString = new GsonBuilder().serializeNulls()
                                                      .setPrettyPrinting()
                                                      .create()
                                                      .toJson(json);

        try (FileWriter writer = new FileWriter(jsonFilePath.toFile())) {
            writer.write(formattedJsonString);
            writer.write(System.lineSeparator());
        }
    }
}
