package com.barisaslan.pethouse.domain.model;

public enum PetType {

    CAT(1, "Cat"),
    DOG(2, "Dog"),
    BIRD(3, "Bird");

    private final int id;
    private final String type;

    PetType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
