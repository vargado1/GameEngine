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

    /**
     * Serializes object into json files.
     *
     * @param data to serialize
     * @param file where to serialize
     * @throws IOException if it was unsuccessful
     */
    public void serializeToFile(Object data, String file) throws IOException {
        try (FileWriter writer = new FileWriter(file);){
            gson.toJson(data, writer);
        }
    }

    /**
     * Deserializes objects from file.
     *
     * @param filePath path to file where data are deserialized from
     * @param type object class that is going to be deserialized
     * @return deserialized object type T
     * @param <T> type of object that should be deserialized
     * @throws IOException if something goes wrong
     */
    public <T> T deserializeFromFile(String filePath, Class<T> type) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        return gson.fromJson(json, type);
    }
}
