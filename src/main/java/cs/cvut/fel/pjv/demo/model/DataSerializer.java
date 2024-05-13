package cs.cvut.fel.pjv.demo.model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataSerializer {
    private Gson gson;

    public DataSerializer() {
        this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

    public void serializeToFile(Object data, String file) throws IOException {
        try (FileWriter writer = new FileWriter(file);){
            gson.toJson(data, writer);
        }
    }

    public <T> T deserializeFromFile(String filePath, Class<T> type) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        return gson.fromJson(json, type);
    }
}
