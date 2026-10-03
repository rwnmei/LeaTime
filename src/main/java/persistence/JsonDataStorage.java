package persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.Catalog;
import domain.Direction;
import domain.TimeEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonDataStorage {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final Path source = Paths.get("data","catalogs.json");
    public void addCatalog(Catalog catalog){
        System.out.println(source.toAbsolutePath());
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
    public void updateCatalog(Catalog catalog){
        List<Catalog> catalogs = loadCatalog();
        for (int i = 0; i < catalogs.size(); i++) {
            if (catalogs.get(i).getId().equals(catalog.getId())){
                catalogs.set(i,catalog);
                break;
            }
        }
        try {
            mapper.writeValue(source.toFile(),catalogs);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Direction> loadAllDirections(){
        List<Catalog> catalogs = loadCatalog();
        List<Direction> directions = new ArrayList<>();

        for(Catalog catalog:catalogs){
            directions.addAll(catalog.getDirections());
        }
        return directions;
    }

    public Catalog findCatalogByDirectionId(UUID directionId) {
        List<Catalog> catalogs = loadCatalog();

        for (Catalog catalog : catalogs) {
            for (Direction direction : catalog.getDirections()) {
                if (direction.getId().equals(directionId)) {
                    return catalog;
                }
            }
        }

        return null;
    }
    public void addTimeEntry(UUID directionId, TimeEntry timeEntry){
        List<Catalog> catalogs = loadCatalog();
        for (Catalog catalog:catalogs){
            for (Direction direction:catalog.getDirections()){
                if (direction.getId().equals(directionId)){
                    direction.getTimeEntries().add(timeEntry);
                    try {
                        mapper.writeValue(source.toFile(), catalogs);
                        System.out.println("Файл сохраняется сюда: " + source.toAbsolutePath());
                        System.out.println("Saved!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        System.out.println("Failed!");
    }
}
