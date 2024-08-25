package org.pancakelab.domain.vo;

public class Building {
    private String name;

    public Building(String name) {
        if (!isValidBuilding(name)) {
            throw new IllegalArgumentException("Invalid building name.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private boolean isValidBuilding(String name) {
        return true;
    }
}
