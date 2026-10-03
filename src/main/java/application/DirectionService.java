package application;

import domain.Catalog;
import domain.Direction;

import java.util.ArrayList;
import java.util.UUID;

public class DirectionService {
    public Direction createDirection(String name, Catalog catalog){
        Direction direction = new Direction(UUID.randomUUID(),name,new ArrayList<>());
        catalog.addDirection(direction);
        return direction;

    }
}
