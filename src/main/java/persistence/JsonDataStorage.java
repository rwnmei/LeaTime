package persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Catalog;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonDataStorage {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final Path source = Paths.get("data","catalogs.json");
    public void addCatalog(Catalog catalog){
        if (!Files.exists(source)){
            try {
                Files.createDirectories(source.getParent());
                mapper.writeValue(source.toFile(),new ArrayList<Catalog>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<Catalog> catalogs = mapper.readValue(source.toFile(), new TypeReference<List<Catalog>>(){});
            catalogs.add(catalog);
            mapper.writeValue(source.toFile(),catalogs);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public List<Catalog> loadCatalog(){
        if (!Files.exists(source)){
            return new ArrayList<>();
        }
        try {
            List<Catalog> catalogs = mapper.readValue(source.toFile(), new TypeReference<List<Catalog>>(){});
            return catalogs;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
