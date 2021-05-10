package com.aslanbaris.pethouse.domain.model;

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

    public static PetType parse(int id) {
        PetType pet = null;
        for (PetType item : PetType.values()) {
            if (item.getId() == id) {
                pet = item;
                break;
            }
        }
        return pet;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
