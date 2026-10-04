package application;

import domain.Catalog;
import java.util.ArrayList;
import java.util.UUID;

public class CatalogService {
    public Catalog createCatalog(String name){
        return new Catalog(UUID.randomUUID(),name,new ArrayList<>());
    }
}
