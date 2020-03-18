package com.mike.routing;

import com.mike.util.Location;

public class Item {
    private final String name;
    public String getName() {
        return this.name;
    }

    private Location pick;
    private Location drop;

    public Item(String name, Location pick, Location drop) {
        this.name = name;
        this.pick = pick;
        this.drop = drop;
    }

}
