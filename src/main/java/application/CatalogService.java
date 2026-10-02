package application;

import domain.Catalog;
import persistence.JsonDataStorage;

import java.util.ArrayList;
import java.util.UUID;

public class CatalogService {
    private final JsonDataStorage storage = new JsonDataStorage();
    public Catalog createCatalog(String name){
        Catalog catalog = new Catalog(UUID.randomUUID(),name,new ArrayList<>());
        return catalog;
    }
}
