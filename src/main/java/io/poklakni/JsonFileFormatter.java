package io.poklakni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonFileFormatter {

    public static void format(Path jsonFilePath) throws IOException {

        String jsonString = new String(Files.readAllBytes(jsonFilePath));
        //map jsonString to LinkedHashMap to preserve the key order
        Map<?, ?> jsonMap = new ObjectMapper().readValue(jsonString, LinkedHashMap.class);
        String formattedJsonString = new GsonBuilder().serializeNulls()
                                                      .setPrettyPrinting()
                                                      .create()
                                                      .toJson(jsonMap);

        try (FileWriter writer = new FileWriter(jsonFilePath.toFile())) {
            writer.write(formattedJsonString);
        }
    }
}
